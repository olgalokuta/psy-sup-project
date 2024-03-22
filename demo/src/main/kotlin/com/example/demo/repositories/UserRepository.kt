package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.User

interface UserRepository : CrudRepository<User, Int> {
    fun findByUsername(uname : String) : List<User>
}