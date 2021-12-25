package com.example.springjdbcrestkotlin.repository

import com.example.springjdbcrestkotlin.model.Cargo
import com.example.springjdbcrestkotlin.util.getIntOrNull
import org.springframework.jdbc.core.*
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class CargoRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) : CargoRepository {

    override fun getAll(pageIndex: Int): List<Cargo> =
        jdbcTemplate.query(
            "select * from cargo order by id limit :limit offset :offset",
            mapOf(
                "limit" to PAGE_SIZE,
                "offset" to pageIndex * PAGE_SIZE,
            ),
            ROW_MAPPER,
        )


    override fun findById(id: Int): Cargo? =
        jdbcTemplate.query(
            "SELECT * FROM cargo WHERE id = :id",
            mapOf(
                "id" to id,
            ),
            ROW_MAPPER
        ).firstOrNull()

    override fun create(title: String, passengerCount: Int?): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "INSERT INTO cargo (title, passenger_count) VALUES (:title, :passengerCount)",
            MapSqlParameterSource(
                mapOf(
                    "title" to title,
                    "passengerCount" to passengerCount,
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun update(id: Int, title: String, passengerCount: Int?) {
        jdbcTemplate.update(
            "update cargo set title = :title, passenger_count = :passengerCount where id = :id",
            mapOf(
                "id" to id,
                "title" to title,
                "passengerCount" to passengerCount,
            )
        )
    }

    override fun deleteById(id: Int) {
        jdbcTemplate.update(
            "DELETE FROM cargo WHERE id = :id",
            mapOf(
                "id" to id,
            )
        )
    }

    override fun getCarStatistics(): Map<String, Int> =
        jdbcTemplate.query(
            """select  cb.title, count(c.id) from cargo c
                | join car_brand cb on c.brand_id =  cb.id 
                | group by cb.title""".trimMargin(),
            EXTRACTOR
        )!!



    private companion object {
        const val PAGE_SIZE = 3
        val ROW_MAPPER = RowMapper<Cargo> { rs, _ ->
            Cargo(
                id = rs.getInt("id"),
                title = rs.getString("title"),
                passengerCount = rs.getIntOrNull("passenger_count"),
            )
        }
    }
    val EXTRACTOR = ResultSetExtractor<Map<String, Int>> {
        rs ->
        val result = mutableMapOf<String, Int>()
        while (rs.next()){
            val title = rs.getString("title")
            result.getOrPut(title){0}
            result[title] = result.getValue(title) + rs.getInt("count")
        }
        result
    }
}