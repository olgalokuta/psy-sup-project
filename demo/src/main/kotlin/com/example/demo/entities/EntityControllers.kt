package com.example.demo.entities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(@Autowired private val userRepository: UserRepository) {

    @GetMapping("")
    fun getAllUsers(): List<User> =
        userRepository.findAll().toList()

    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val createdUser = userRepository.save(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        val user = userRepository.findById(userId).orElse(null)
        return if (user != null) ResponseEntity(user, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Int, @RequestBody user: User): ResponseEntity<User> {

        val existingUser = userRepository.findById(userId).orElse(null)

        if (existingUser == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedUser = existingUser.copy(username = user.username, 
            email = user.email, phone = user.phone)
        userRepository.save(updatedUser)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        userRepository.deleteById(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}

@RestController
@RequestMapping("/api/posts")
class PostController(@Autowired private val postRepository: PostRepository) {

    @GetMapping("")
    fun getAllPosts(): List<Post> =
        postRepository.findAll().toList()

    @GetMapping("/user/{uid}")
    fun getAllUserPosts(@PathVariable("uid") userId: Int): List<Post> =
        postRepository.findByIduser(userId).toList()

    @PostMapping("")
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

        val updatedPost = existingPost.copy(iduser = post.iduser, posted = post.posted, content = post.content)
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