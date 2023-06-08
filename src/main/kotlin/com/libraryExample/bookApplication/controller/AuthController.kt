package com.libraryExample.bookApplication.controller

import com.libraryExample.bookApplication.dtos.LoginDTO
import com.libraryExample.bookApplication.dtos.Message
import com.libraryExample.bookApplication.dtos.RegisterDTO
import com.libraryExample.bookApplication.model.User
import com.libraryExample.bookApplication.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class AuthController(private val service: UserService) {
    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password

        return ResponseEntity.ok(this.service.save(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO): ResponseEntity<Any> {
        val user =
            this.service.findByEmail(body.email) ?: return ResponseEntity.badRequest().body(Message("User not found"))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(Message("Invalid password"))
        }
        return ResponseEntity.ok(user)
    }

}