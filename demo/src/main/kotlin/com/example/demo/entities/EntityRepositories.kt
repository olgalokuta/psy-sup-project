package com.example.demo.entities

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
    fun findByUsername(uname : String) : List<User>
}

interface PostRepository : CrudRepository<Post, Int> {
    fun findByIduser(userId : Int) : List <Post>
    fun findByPublic(public : Boolean) : List <Post>
}