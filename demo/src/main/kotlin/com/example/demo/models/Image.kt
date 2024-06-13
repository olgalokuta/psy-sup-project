package com.example.demo.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "images")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val directory: String,
    val bin: String
)
