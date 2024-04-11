package com.example.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.demo.repositories.EntryRepository
import com.example.demo.models.Entry

@RestController
@CrossOrigin(origins=["http://localhost:3000"])
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
        entryRepository.findByPublicAndModerated(true,true).toList()

    @GetMapping("/formoderation")
    fun getUnmoderatedEntries():List<Entry> = 
        entryRepository.findByPublicAndModerated(true, false).toList()

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