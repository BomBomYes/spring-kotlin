package com.tdmnsExamlpe.bookApplication.datasource.mock

import com.tdmnsExamlpe.bankApplication.model.Book
import com.tdmnsExamlpe.bookApplication.datasource.BookDataSource
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockBookDataSource: BookDataSource {

    val books = mutableListOf(
        Book(1, "War and Peace", "Leo Tolstoy", "Great novel"),
        Book(2, "1984", "George Orwell", "Dystopian classic"),
        Book(3, "Moby Dick", "Herman Melville", "Epic sea story")
    )

    override fun retrieveBooks(): Collection<Book> = books

    override fun retrieveBook(id: Int): Book = books.firstOrNull() { it.id == id }
        ?: throw NoSuchElementException("Could not find a book with id $id")

    override fun createBook(book: Book): Book {
        if (books.any {it.id == book.id}) {
            throw IllegalArgumentException("Book with id ${book.id} already exist")
        }
        books.add(book)

        return book
    }

    override fun updateBook(book: Book): Book {
        val currentBook = books.firstOrNull() { it.id == book.id }
            ?: throw NoSuchElementException("Could not find a book with id ${book.id}")

        books.remove(currentBook)
        books.add(book)

        return book
    }

    override fun deleteBook(id: Int) {
        val currentBook = books.firstOrNull() { it.id == id }
            ?: throw NoSuchElementException("Could not find a book with id $id")

        books.remove(currentBook)
    }
}
