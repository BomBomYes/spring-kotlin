package com.libraryExample.bookApplication.controller

import com.libraryExample.bookApplication.model.Book
import com.libraryExample.bookApplication.service.BookService
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.ByteArrayInputStream

@RestController
@RequestMapping("api")
class BookController(private val service: BookService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping("/books")
    fun getBooks(model: Model): List<Book> = service.getBooks()

    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Int): Book = service.getBook(id.toLong())

    @PostMapping("/books/add")
    fun addBook(
        @RequestParam("title") title: String,
        @RequestParam("author") author: String,
        @RequestParam("review") review: String,
        @RequestParam("published_date") publishedDate: String,
        @RequestParam("cover_image") coverImage: MultipartFile,
        redirectAttributes: RedirectAttributes
    ): String {
        val book = Book()
        book.title = title
        book.author = author
        book.review = review
        book.publishedDate = publishedDate
        book.coverImage = coverImage.bytes
        service.addBook(book)
        redirectAttributes.addFlashAttribute("message", "Book has been added successfully.")
        return "redirect:/books"
    }

    @GetMapping("/books/{id}/cover")
    fun getCoverImage(@PathVariable id: Int): ResponseEntity<InputStreamResource> {
        val book = service.getBook(id.toLong())
        val img = ByteArrayInputStream(book.coverImage)
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(InputStreamResource(img))
    }

    @PatchMapping
    fun updateBook(@RequestBody book: Book): Book = service.updateBook(book)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: Int): Unit = service.deleteBook(id.toLong())
}
