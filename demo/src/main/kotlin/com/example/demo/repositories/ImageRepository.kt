package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.Image

interface ImageRepository : CrudRepository<Image, Int> {

}
