package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.Topic

interface TopicsRepository : CrudRepository<Topic, Int>