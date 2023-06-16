package com.libraryExample.bookApplication.service

import com.libraryExample.bookApplication.model.Book
import com.libraryExample.bookApplication.repositories.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {
    fun getBooks(): List<Book> = bookRepository.findAll()
    fun getBook(id: Long): Book = bookRepository.findById(id).orElseThrow { NoSuchElementException("Book not found") }
    fun addBook(book: Book): Book = bookRepository.save(book)
    fun updateBook(book: Book): Book {
        val existingBook = bookRepository.findById(book.id.toLong())
            .orElseThrow { NoSuchElementException("Book not found") }
        existingBook.title = book.title
        existingBook.author = book.author
        existingBook.publishedDate = book.publishedDate
        return bookRepository.save(existingBook)
    }
    fun deleteBook(id: Long) {
        bookRepository.deleteById(id)
    }
}
