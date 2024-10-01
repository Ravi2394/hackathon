package com.example.firstcompose.model

data class LoginResponse(
    val data: List<Data>,
    val msg: String,
    val status: Int
)