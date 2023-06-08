package com.libraryExample.bookApplication.controller

import com.libraryExample.bookApplication.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class AuthController(private val service: UserService) {
    @GetMapping("register")
    fun register(): String {
        return "register"
    }
}