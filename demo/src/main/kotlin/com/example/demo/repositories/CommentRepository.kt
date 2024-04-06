package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.Comment

interface CommentRepository : CrudRepository<Comment, Int> {
    fun findByIdentry(entryId : Int) : List <Comment>
    fun findByModerated(mod : Boolean) : List<Comment>
}