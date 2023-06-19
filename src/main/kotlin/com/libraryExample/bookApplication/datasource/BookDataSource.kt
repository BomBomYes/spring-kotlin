package com.libraryExample.bookApplication.datasource

import com.libraryExample.bookApplication.model.Book


interface BookDataSource {
    fun retrieveBooks(): Collection<Book>

    fun retrieveBook(id: Int): Book

    fun createBook(book: Book): Book

    fun updateBook(book: Book): Book

    fun deleteBook(id: Int): Unit
}
