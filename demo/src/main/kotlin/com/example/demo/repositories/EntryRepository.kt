package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.Entry
import com.example.demo.models.Visibility

interface EntryRepository : CrudRepository<Entry, Int> {
    fun findByIduser(userId : Int) : List <Entry>
    fun findByVisibility(visibility : Visibility) : List <Entry>
    // fun findByVisibilityAndModeratedOrderByPostedDesc(pub : Visibility, mod: Boolean) : List <Entry>
    // fun findByVisibilityAndModeratedOrderByPostedAsc(pub : Visibility, mod: Boolean) : List <Entry>
    // fun findByModeratorAndModerated(modId: Int, finished: Boolean): List<Entry>
}
