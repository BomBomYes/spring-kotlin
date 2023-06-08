package com.libraryExample.bookApplication.service

import com.libraryExample.bookApplication.model.User
import com.libraryExample.bookApplication.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    //fun save user
    fun save(user: User): User {
        return userRepository.save(user)
    }
}