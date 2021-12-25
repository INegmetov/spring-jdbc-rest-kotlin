package com.example.springjdbcrestkotlin.dto

data class CargoDto(
    val id: Int = 0,
    val title: String,
    val passengerCount: Int? = null

) {
}