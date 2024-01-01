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

/*
data class Product(
    val id : Int,
    val title : String,
    val description : String,
    val price : Int,
    val discountPercentage : Float,
    val rating : Float,
    val stock : Int,
    val brand : String,
    val category: String,
    val thumbnail : String,
    val images: List<String>
)*/