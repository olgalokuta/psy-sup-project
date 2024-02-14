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

data class Entry(
    var id: Int,
    var iduser: Int,
    var posted: String,
    var content: String,
    var moderated: Boolean,
    var public: Boolean,
    var topics: List<Int>
)