package com.example.demo.posts


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(@Autowired private val postRepository: PostRepository) {

    @GetMapping("")
    fun getAllPosts(): List<Post> =
        postRepository.findAll().toList()

    @PutMapping("")
    fun createPost(@RequestBody post: Post): ResponseEntity<Post> {
        val createdPost = postRepository.save(post)
        return ResponseEntity(createdPost, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable("id") postId: Int): ResponseEntity<Post> {
        val post = postRepository.findById(postId).orElse(null)
        return if (post != null) ResponseEntity(post, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updatePostById(@PathVariable("id") postId: Int, @RequestBody post: Post): ResponseEntity<Post> {

        val existingPost = postRepository.findById(postId).orElse(null)

        if (existingPost == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedPost = existingPost.copy(userid = post.userid, date = post.date, text = post.text)
        postRepository.save(updatedPost)
        return ResponseEntity(updatedPost, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deletePostById(@PathVariable("id") postId: Int): ResponseEntity<Post> {
        if (!postRepository.existsById(postId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        postRepository.deleteById(postId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}