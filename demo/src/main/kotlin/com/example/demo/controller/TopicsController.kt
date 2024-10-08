package com.example.demo.controller

import com.example.demo.models.Topic
import com.example.demo.repositories.TopicsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/topics")
class TopicsController(@Autowired private val topicsRepository: TopicsRepository) {
    init {
        // Если тем нет, то вставляются стандартные
        if (topicsRepository.count() == 0.toLong()) {
            topicsRepository.save(Topic(id = 0, topic = "Психология"))
            topicsRepository.save(Topic(id = 1, topic = "Рассуждения"))
            topicsRepository.save(Topic(id = 2, topic = "Разное"))
        }
    }

    @GetMapping("")
    fun getAllTopics(): ResponseEntity<List<Topic>> =
        ResponseEntity(topicsRepository.findAll().toList(), HttpStatus.OK) // Получение всех тем
}