package com.example.lms.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,
    val userName: String,
    val uerEmail: String,
    val role: String
)