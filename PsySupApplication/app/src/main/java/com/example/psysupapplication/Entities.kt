package com.example.psysupapplication

import android.adservices.topics.Topic

data class User (
    var id: Int,
    var username: String,
    var email: String,
    var phone: String
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