package com.example.demo.service

import com.example.demo.models.User
import com.example.demo.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
  private val userRepository: UserRepository
) {
  fun createUser(user: User): User? {
    val found = userRepository.findByEmail(user.email)
    return if (found == null) {
      userRepository.save(user)
      user
    } else null
  }
  fun findById(uid: Int): User? =
    userRepository.findById(uid).orElse(null)
  fun findAll(): List<User> =
    userRepository.findAll().toList()
  fun findByUsername(username: String): User? {
    val found = userRepository.findByUsername(username).toList()
    return if (found != null) {
        found[0]
    } else null
  }
  fun updateById(userId: Int, user: User) : User? {
    val existingUser = userRepository.findById(userId).orElse(null)
        if (existingUser == null) {
            return null
        }
        val updatedUser = existingUser.copy(username = user.username, 
            email = user.email, phone = user.phone, password = user.password,
            birthday = user.birthday, pfp = user.pfp, gender = user.gender, 
            topics = user.topics)
        userRepository.save(updatedUser)
        return updatedUser
  }
  fun deleteById(uid: Int): Boolean {
    if (!userRepository.existsById(uid)) {
      return false
    }
    userRepository.deleteById(uid)
    return true
  }
}