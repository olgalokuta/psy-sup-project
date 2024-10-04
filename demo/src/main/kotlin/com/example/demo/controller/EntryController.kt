package com.example.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.demo.repositories.EntryRepository
import com.example.demo.models.Entry
import com.example.demo.models.Visibility
import org.apache.tomcat.util.codec.binary.Base64
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/entries")
class EntryController(@Autowired private val entryRepository: EntryRepository) {
    data class EntryDto(
        val id: Int?,
        val iduser: Int,
        val posted: LocalDateTime,
        val content: String,
        val moderated: Boolean,
        val moderator: Int?,
        val visibility: Visibility,
        val topics: List<Int>,
        val photos: List<String>
    ) {
        fun toEntry(): Entry = Entry(null, iduser, posted, content, moderated, null, visibility, topics, photos.map {
            Base64.decodeBase64(it)
        })
    }

    @GetMapping("")
    fun getAllEntries(): List<Entry> =
        entryRepository.findAll().toList()

    @GetMapping("/user/{uid}")
    fun getAllUserEntries(@PathVariable("uid") userId: Int): List<Entry> =
        entryRepository.findByIduser(userId).toList()

    @GetMapping("/public")
    fun getAllPublicEntries():List<Entry> =
        entryRepository.findByVisibility(Visibility.public).toList()

    @GetMapping("/public/topic/{topicId}") // Получение публичных постов с темой
    fun getAllPublicEntriesWithTopic(@PathVariable("topicId") topicId: Int): List<Entry> =
        entryRepository.getPublicWithTopic(topicId).toList()

    @PostMapping("")
    fun createEntry(@RequestBody entry: EntryDto): ResponseEntity<Entry> {
        val createdEntry = entryRepository.save(Entry(null, entry.iduser, entry.posted, entry.content, entry.moderated, null, entry.visibility, entry.topics, entry.photos.map {
            Base64.decodeBase64(it)
        }))
        return ResponseEntity(createdEntry, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getEntryById(@PathVariable("id") entryId: Int): ResponseEntity<Entry> {
        val entry = entryRepository.findById(entryId).orElse(null)
        return if (entry != null) ResponseEntity(entry, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    /*
    @PutMapping("/{id}")
    fun updateEntryById(@PathVariable("id") entryId: Int, @RequestBody entry: Entry): ResponseEntity<Entry> {
        val existingEntry = entryRepository.findById(entryId).orElse(null) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val updatedEntry = existingEntry.copy(iduser = entry.iduser, posted = entry.posted,
            content = entry.content, moderated = entry.moderated, visibility = entry.visibility,
            topics = entry.topics, photos = entry.photos)
        entryRepository.save(updatedEntry)
        return ResponseEntity(updatedEntry, HttpStatus.OK)
    }
    */

    @DeleteMapping("/{id}")
    fun deleteEntryById(@PathVariable("id") entryId: Int): ResponseEntity<Entry> {
        if (!entryRepository.existsById(entryId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        entryRepository.deleteById(entryId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
