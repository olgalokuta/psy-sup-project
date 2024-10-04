package com.example.psysupapplication

data class User (
    val id: Int,
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val pfp: Int,
    val gender: Boolean,
    val birthday: String,
    val topics: List<Int>
)

data class Entry(
    var id: Int,
    var iduser: Int,
    var posted: String,
    var content: String,
    var moderated: Boolean,
    var visibility: String,
    var topics: List<Int>,
    val photos: List<String>
)

data class Comment(
    val id: Int,
    val iduser: Int,
    val posted: String,
    val content: String,
    val moderated: Boolean,
    val identry: Int,
    val idanscomment: Int,
    val photo: String? // Прикрепленная фотография
)

data class Topic( // Тема
    val id: Int,
    val topic: String,
)