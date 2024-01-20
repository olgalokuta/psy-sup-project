package com.example.demo.entities

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val birthday: LocalDate,
    val pfp: Int,
    val gender: Boolean,
    val topics: List <Int>
)

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val iduser: Int,
    val posted: LocalDateTime,
    val content: String, 
    val moderated: Boolean, 
    val public: Boolean,
    val topics: List<Int>
)

@Entity
@Table(name = "comments")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val iduser: Int,
    val posted: LocalDateTime,
    val content: String, 
    val moderated: Boolean, 
    val idpost: Int,
    val idanscomment: Int
)

@Entity
@Table(name = "topics")
data class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val topic: String
)

@Entity
@Table(name = "status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val status: Boolean
)

@Entity
@Table(name = "userstatus")
data class UserStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val iduser: Int,
    val idstatus: Int,
    val timebeg: LocalDateTime,
    val timeend: LocalDateTime
)



