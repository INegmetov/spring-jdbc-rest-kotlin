package com.example.springjdbcrestkotlin.service

import com.example.springjdbcrestkotlin.dto.CargoDto

interface CargoService {

    fun getAll(pageIndex: Int):List<CargoDto>

    fun getById( id: Int): CargoDto

    fun create( dto: CargoDto): Int

    fun update( id: Int,  dto: CargoDto)

    fun deleteById ( id: Int)

    fun getCarStatistics(): Map<String, Int>

}