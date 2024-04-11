package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.Entry

interface EntryRepository : CrudRepository<Entry, Int> {
    fun findByIduser(userId : Int) : List <Entry>
    fun findByPublicAndModerated(pub : Boolean, mod: Boolean) : List <Entry>
}
