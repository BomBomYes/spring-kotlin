package com.libraryExample.bookApplication.datasource.mock

import com.libraryExample.bookApplication.datasource.BookDataSource
import com.libraryExample.bookApplication.model.Book
import org.springframework.stereotype.Repository

@Repository
class MockBookDataSource : BookDataSource {

    val books = mutableListOf(
        Book(1, "The Hobbit", "J.R.R. Tolkien", "A great book about a hobbit"),
        Book(2, "The Fellowship of the Ring", "J.R.R. Tolkien", "A great book about a fellowship"),
        Book(3, "The Two Towers", "J.R.R. Tolkien", "A great book about two towers"),
    )

    override fun retrieveBooks(): Collection<Book> = books

    override fun retrieveBook(id: Int): Book =
        books.firstOrNull { it.id == id } ?: throw NoSuchElementException("Could not find book with id $id")

    override fun createBook(book: Book): Book {
        if (books.any { it.id == book.id }) {
            throw IllegalArgumentException("Book with id ${book.id} already exists")
        }
        books.add(book)
        return book
    }

    override fun updateBook(book: Book): Book {
        val currentBook = books.firstOrNull { it.id == book.id }
            ?: throw NoSuchElementException("Could not find book with id ${book.id}")

        books.remove(currentBook)
        books.add(book)

        return book
    }

    override fun deleteBook(id: Int) {
        val currentBook =
            books.firstOrNull { it.id == id } ?: throw NoSuchElementException("Could not find book with id $id")

        books.remove(currentBook)
    }
}