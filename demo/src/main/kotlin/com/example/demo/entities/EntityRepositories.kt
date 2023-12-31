package com.example.demo.entities

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int>

interface PostRepository : CrudRepository<Post, Int> {
    fun findByIduser(userId : Int) : List <Post>
}