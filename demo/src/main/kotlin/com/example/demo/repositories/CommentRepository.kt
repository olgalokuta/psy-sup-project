package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.Comment

interface CommentRepository : CrudRepository<Comment, Int> {
    fun findByIdentry(entryId : Int) : List <Comment>
    fun findByModeratedOrderByPostedAsc(mod : Boolean) : List<Comment>
    fun findByModeratorAndModerated(modId: Int, mod: Boolean):List<Comment>
}