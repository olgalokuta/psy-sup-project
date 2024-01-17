package com.example.psysupapplication

data class User (
    var id: Int,
    var username: String,
    var email: String,
    var phone: String
)

data class Post(
    val id: Int,
    val userid: Int,
    val date: String,
    val text: String
)