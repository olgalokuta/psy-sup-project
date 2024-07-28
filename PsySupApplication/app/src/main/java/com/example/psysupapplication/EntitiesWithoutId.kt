package com.example.psysupapplication

data class UserWithoutId(
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val pfp: Int,
    val gender: Boolean,
    val birthday: String,
    val topics: List<Int>
)

data class EntryWithoutId(
    val iduser: Int,
    val posted: String,
    val content: String,
    val moderated: Boolean,
    val visibility: String,
    val topics: List<Int>,
    val photos: List<String>
)
data class CommentWithoutId(
    val iduser: Int,
    val posted: String,
    val content: String,
    val moderated: Boolean,
    val identry: Int,
    val idanscomment: Int
)
