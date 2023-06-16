package com.libraryExample.bookApplication.datasource.mock

import com.libraryExample.bookApplication.datasource.BookDataSource
import com.libraryExample.bookApplication.model.Book
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class MockBookDataSource : BookDataSource {

    val books = mutableListOf(
        Book().apply {
            id = 1
            title = "The Hobbit"
            author = "J.R.R. Tolkien"
            review = "A great book about a hobbit"
            publishedDate = LocalDate.of(1937, 9, 21).toString()
            coverImage = null
        },
        Book().apply {
            id = 2
            title = "The Fellowship of the Ring"
            author = "J.R.R. Tolkien"
            review = "A great book about a fellowship"
            publishedDate = LocalDate.of(1954, 7, 29).toString()
            coverImage = null
        },
        Book().apply {
            id = 3
            title = "The Two Towers"
            author = "J.R.R. Tolkien"
            review = "A great book about two towers"
            publishedDate = LocalDate.of(1954, 11, 11).toString()
            coverImage = null
        },
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
