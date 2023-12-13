package com.example.demo.posts

import jakarta.persistence.*

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val userid: Int,
    val date: String,
    val text: String,
)