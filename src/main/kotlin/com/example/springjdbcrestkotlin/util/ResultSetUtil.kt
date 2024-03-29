package com.example.springjdbcrestkotlin.util

import java.sql.ResultSet

fun ResultSet.getIntOrNull(columnLabel: String): Int?{
    val value = this.getInt(columnLabel)
    return if (this.wasNull())
        null
    else{
        value
    }
}