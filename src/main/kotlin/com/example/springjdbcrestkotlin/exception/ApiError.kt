package com.example.springjdbcrestkotlin.exception

data class ApiError(
    val errorCode: String,
    val message: String,
)
