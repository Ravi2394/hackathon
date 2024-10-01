package com.example.firstcompose.model

data class EmployeeResponse(
    val data: List<DataX>,
    val msg: String,
    val status: Int
)