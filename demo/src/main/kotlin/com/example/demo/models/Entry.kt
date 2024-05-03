package com.example.demo.models

import jakarta.persistence.*
import java.time.LocalDateTime

enum class Visibility {public, private, psychologist}

@Entity
@Table(name = "entries")
data class Entry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val iduser: Int,
    val posted: LocalDateTime,
    val content: String, 
    val moderated: Boolean,
    val moderator: Int?,
    val visibility: Visibility,
    val topics: List<Int>
)