package com.example.demo.models

import jakarta.persistence.*
import java.time.LocalDateTime

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