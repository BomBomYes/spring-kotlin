package com.libraryExample.bookApplication

import com.libraryExample.bookApplication.dtos.LoginDTO
import com.libraryExample.bookApplication.dtos.RegisterDTO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {

    @GetMapping("/")
    fun index(model: Model): String {
        return "index"
    }

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("loginDTO", LoginDTO())
        return "login"
    }

    @GetMapping("/register")
    fun register(model: Model): String {
        model.addAttribute("registerDTO", RegisterDTO())
        return "register"
    }
}
