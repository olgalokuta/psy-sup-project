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

    @GetMapping("/name/{un}")
    fun getUserByUsername(@PathVariable("un") username: String): ResponseEntity<User> {
        val user = userRepository.findByUsername(username).toList()
        return if (user != null) ResponseEntity(user[0], HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
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
            email = user.email, phone = user.phone, password = user.password,
            birthday = user.birthday, pfp = user.pfp, gender = user.gender, 
            topics = user.topics)
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
@RequestMapping("/api/entries")
class EntryController(@Autowired private val entryRepository: EntryRepository) {

    @GetMapping("")
    fun getAllEntries(): List<Entry> =
        entryRepository.findAll().toList()

    @GetMapping("/user/{uid}")
    fun getAllUserEntries(@PathVariable("uid") userId: Int): List<Entry> =
        entryRepository.findByIduser(userId).toList()

    @GetMapping("/public")
    fun getAllPublicEntries():List<Entry> = 
        entryRepository.findByPublic(true).toList()

    @PostMapping("")
    fun createEntry(@RequestBody entry: Entry): ResponseEntity<Entry> {
        val createdEntry = entryRepository.save(entry)
        return ResponseEntity(createdEntry, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getEntryById(@PathVariable("id") entryId: Int): ResponseEntity<Entry> {
        val entry = entryRepository.findById(entryId).orElse(null)
        return if (entry != null) ResponseEntity(entry, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateEntryById(@PathVariable("id") entryId: Int, @RequestBody entry: Entry): ResponseEntity<Entry> {

        val existingEntry = entryRepository.findById(entryId).orElse(null)

        if (existingEntry == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedEntry = existingEntry.copy(iduser = entry.iduser, posted = entry.posted, 
            content = entry.content, moderated = entry.moderated, public = entry.public, 
            topics = entry.topics)
        entryRepository.save(updatedEntry)
        return ResponseEntity(updatedEntry, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteEntryById(@PathVariable("id") entryId: Int): ResponseEntity<Entry> {
        if (!entryRepository.existsById(entryId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        entryRepository.deleteById(entryId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}