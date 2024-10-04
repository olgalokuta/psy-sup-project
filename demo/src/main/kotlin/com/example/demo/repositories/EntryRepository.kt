package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.Entry
import com.example.demo.models.Visibility
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface EntryRepository : CrudRepository<Entry, Int> {
    fun findByIduser(userId : Int) : List <Entry>
    fun findByVisibility(visibility : Visibility) : List <Entry>
    @Query(value = "select * from entries where visibility = 0 and :topicId = any(topics)", nativeQuery = true) // Получить публичные посты с темой topicId
    fun getPublicWithTopic(@Param("topicId") topicId: Int) : List<Entry>
    // fun findByVisibilityAndModeratedOrderByPostedDesc(pub : Visibility, mod: Boolean) : List <Entry>
    // fun findByVisibilityAndModeratedOrderByPostedAsc(pub : Visibility, mod: Boolean) : List <Entry>
    // fun findByModeratorAndModerated(modId: Int, finished: Boolean): List<Entry>
}
