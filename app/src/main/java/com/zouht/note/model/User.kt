package com.zouht.note.model

data class User(
    val userId: Int,
    val email: String,
    val username: String,
    val password: String,
    val avatar: String
)
