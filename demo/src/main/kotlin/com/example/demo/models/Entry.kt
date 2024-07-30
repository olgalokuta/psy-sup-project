package com.example.demo.models

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Type
import org.hibernate.engine.jdbc.env.internal.LobTypes
import java.time.LocalDateTime

@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class Visibility { public, private, psychologist}

@Entity
@Table(name = "entries")
data class Entry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val iduser: Int,
    val posted: LocalDateTime,
    val content: String,
    val moderated: Boolean,
    val moderator: Int?,
    val visibility: Visibility,
    val topics: List<Int>,
    @Column(columnDefinition="bytea")
    val photos: List<ByteArray>
)