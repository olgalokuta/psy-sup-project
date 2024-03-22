package com.example.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.demo.repositories.CommentRepository
import com.example.demo.models.Comment

@RestController
@RequestMapping("/api/comments")
class CommentController(@Autowired private val commentRepository: CommentRepository) {

    @GetMapping("")
    fun getAllComments(): List<Comment> =
        commentRepository.findAll().toList()

    @GetMapping("/entry/{eid}")
    fun getAllEntryComments(@PathVariable("eid") entryId: Int): List<Comment> =
        commentRepository.findByIdentry(entryId).toList()

    @PostMapping("")
    fun createComment(@RequestBody comment: Comment): ResponseEntity<Comment> {
        val createdComment = commentRepository.save(comment)
        return ResponseEntity(createdComment, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getCommentById(@PathVariable("id") commentId: Int): ResponseEntity<Comment> {
        val comment = commentRepository.findById(commentId).orElse(null)
        return if (comment != null) ResponseEntity(comment, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateCommentById(@PathVariable("id") commentId: Int, @RequestBody comment: Comment): ResponseEntity<Comment> {

        val existingComment = commentRepository.findById(commentId).orElse(null)

        if (existingComment == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedComment = existingComment.copy(iduser = comment.iduser, posted = comment.posted, 
            content = comment.content, moderated = comment.moderated, identry = comment.identry, 
            idanscomment = comment.idanscomment)
        commentRepository.save(updatedComment)
        return ResponseEntity(updatedComment, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteCommentById(@PathVariable("id") commentId: Int): ResponseEntity<Comment> {
        if (!commentRepository.existsById(commentId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        commentRepository.deleteById(commentId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}