package com.libraryExample.bookApplication.service

import com.libraryExample.bookApplication.datasource.BookDataSource
import com.libraryExample.bookApplication.model.Book
import org.springframework.stereotype.Service

@Service
class BookService(private val dataSource: BookDataSource) {
    fun getBooks(): Collection<Book> = dataSource.retrieveBooks()
    fun getBook(id: Int) = dataSource.retrieveBook(id)
    fun addBook(book: Book): Book = dataSource.createBook(book)
    fun updateBook(book: Book): Book = dataSource.updateBook(book)
    fun deleteBook(id: Int): Unit = dataSource.deleteBook(id)
}
