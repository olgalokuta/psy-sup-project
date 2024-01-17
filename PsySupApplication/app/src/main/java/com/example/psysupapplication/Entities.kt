package com.example.psysupapplication

data class User (
    var id: Int,
    var username: String,
    var email: String,
    var phone: String,
    var password: String,
    var pfp: Int,
    var gender: Boolean,
    var birthday: String,
    var topics: List<Int>
)

data class Post(
    val id: Int,
    val iduser: Int,
    val posted: String,
    val content: String,
    val moderated: Boolean,
    val public: Boolean,
    val topics: List<Int>
)