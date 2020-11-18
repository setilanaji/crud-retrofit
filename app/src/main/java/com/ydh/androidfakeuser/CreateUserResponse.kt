package com.ydh.androidfakeuser

data class CreateUserResponse(
    val name: String,
    val job: String,
    val id: Int,
    val createdAt: String
)