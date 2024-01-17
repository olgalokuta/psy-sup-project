package com.example.psysupapplication
data class UserWithoutId(
    var username: String,
    var email: String,
    var phone: String
)

data class PostWithoutId(
    val iduser: Int,
    val posted: String,
    val content: String,
    val moderated: Boolean,
    val public: Boolean,
    val topics: List<Int>
)

