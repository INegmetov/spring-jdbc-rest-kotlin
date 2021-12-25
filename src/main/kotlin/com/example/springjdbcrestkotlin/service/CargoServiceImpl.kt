package com.example.springjdbcrestkotlin.service

import com.example.springjdbcrestkotlin.dto.CargoDto
import com.example.springjdbcrestkotlin.exception.CargoNotFoundException
import com.example.springjdbcrestkotlin.model.Cargo
import com.example.springjdbcrestkotlin.repository.CargoRepository
import org.springframework.stereotype.Service

@Service
class CargoServiceImpl(
    private val cargoRepository: CargoRepository
) : CargoService {
    override fun getAll(pageIndex: Int): List<CargoDto> =
        cargoRepository.getAll(pageIndex).map {it.toDto()}

    override fun getById(id: Int): CargoDto = cargoRepository.findById(id)?.toDto()
        ?: throw CargoNotFoundException(id)

    override fun create(dto: CargoDto): Int =
        cargoRepository.create(dto.title, dto.passengerCount)

    override fun update(id: Int, dto: CargoDto) {
        cargoRepository.update(id, dto.title, dto.passengerCount)
    }

    override fun deleteById(id: Int) {
        cargoRepository.deleteById(id)
    }

    override fun getCarStatistics(): Map<String, Int> =
        cargoRepository.getCarStatistics()


    private fun Cargo.toDto() = CargoDto(
        id = id,
        title = title,
        passengerCount = passengerCount,
    )
}