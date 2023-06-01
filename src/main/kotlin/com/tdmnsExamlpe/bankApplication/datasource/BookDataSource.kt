package com.tdmnsExamlpe.bookApplication.datasource

import com.tdmnsExamlpe.bankApplication.model.Book


interface BookDataSource {

    fun retrieveBooks(): Collection<Book>

    fun retrieveBook(id: Int): Book

    fun createBook(book: Book): Book

    fun updateBook(book: Book): Book

    fun deleteBook(id: Int): Unit
}
