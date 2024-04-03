package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.User
//import org.springframework.security.crypto.password.PasswordEncoder


interface UserRepository : CrudRepository<User, Int>  {
    fun findByUsername(uname : String) : List<User>
    fun findByEmail(email: String): User?
}