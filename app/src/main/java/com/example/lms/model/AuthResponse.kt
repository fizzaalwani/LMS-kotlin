package com.example.lms.model

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData?
)

data class AuthData(
    val accessToken: String,
    val user: User
)