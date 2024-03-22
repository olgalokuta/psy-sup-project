package com.example.demo.models

import jakarta.persistence.*
import java.time.LocalDate

enum class Role {
    USE, MOD, PSY
}


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
    val topics: List <Int>,
    val role: Role
)

