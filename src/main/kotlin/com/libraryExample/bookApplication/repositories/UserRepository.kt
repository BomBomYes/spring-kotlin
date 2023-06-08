package com.libraryExample.bookApplication.repositories

import com.libraryExample.bookApplication.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
}