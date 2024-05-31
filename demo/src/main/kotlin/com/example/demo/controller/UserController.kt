package com.example.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.demo.models.User
import com.example.demo.service.UserService

@RestController
@CrossOrigin(origins=["http://localhost:3000", "http://62.3.58.13"])
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping("")
    fun getAllUsers(): ResponseEntity<List<User>> =
        ResponseEntity(userService.findAll(), HttpStatus.OK)

    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val createdUser = userService.createUser(user)
        return if (createdUser != null) ResponseEntity(createdUser, HttpStatus.CREATED)
            else ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/name/{un}")
    fun getUserByUsername(@PathVariable("un") username: String): ResponseEntity<User> {
        val user = userService.findByUsername(username)
        return if (user != null) ResponseEntity(user, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        val user = userService.findById(userId)
        return if (user != null) ResponseEntity(user, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Int, @RequestBody user: User): ResponseEntity<User> {
        val user = userService.updateById(userId, user)
        return if (user != null) ResponseEntity(user, HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        return if (userService.deleteById(userId)) ResponseEntity(HttpStatus.NO_CONTENT)
        else ResponseEntity(HttpStatus.NOT_FOUND)
    }
}