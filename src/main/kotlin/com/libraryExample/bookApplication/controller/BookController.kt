package com.libraryExample.bookApplication.controller


import com.libraryExample.bookApplication.model.Book
import com.libraryExample.bookApplication.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class BookController(private val service: BookService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getBooks(): Collection<Book> = service.getBooks()

    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Int): Book = service.getBook(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody book: Book): Book = service.addBook(book)

    @PatchMapping
    fun updateBook(@RequestBody book: Book): Book = service.updateBook(book)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: Int): Unit = service.deleteBook(id)
}