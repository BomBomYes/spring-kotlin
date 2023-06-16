package com.libraryExample.bookApplication

import com.libraryExample.bookApplication.dtos.LoginDTO
import com.libraryExample.bookApplication.dtos.RegisterDTO
import com.libraryExample.bookApplication.model.Book
import com.libraryExample.bookApplication.repositories.BookRepository
import com.libraryExample.bookApplication.service.BookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class WebController(private val service: BookService) {


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

    @GetMapping("/books")
    fun getBooks(model: Model): String {
        val books = service.getBooks()
        model.addAttribute("books", books)
        return "books"
    }

    @PostMapping("/books/add")
    fun addBook(
        @RequestParam("title") title: String,
        @RequestParam("author") author: String,
        @RequestParam("review") review: String,
        @RequestParam("publishedDate") publishedDate: String,
        @RequestParam("coverImage") coverImage: MultipartFile,
        redirectAttributes: RedirectAttributes
    ): String {
        val book = Book()
        book.title = title
        book.author = author
        book.review = review
        book.publishedDate = publishedDate
        book.coverImage = coverImage.bytes
        service.addBook(book)  // Сохранить книгу
        redirectAttributes.addFlashAttribute("message", "Book has been added successfully.")
        return "redirect:/books"
    }
}
