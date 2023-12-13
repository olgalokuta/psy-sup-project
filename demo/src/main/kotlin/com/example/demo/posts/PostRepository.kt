package com.example.demo.posts

import org.springframework.data.repository.CrudRepository

interface PostRepository : CrudRepository<Post, Int>