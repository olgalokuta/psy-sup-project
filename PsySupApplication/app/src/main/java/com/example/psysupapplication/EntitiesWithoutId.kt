package com.example.psysupapplication
data class UserWithoutId(
    var username: String,
    var email: String,
    var phone: String
)

data class PostWithoutId(
    val userid: Int,
    val date: String,
    val text: String
)

