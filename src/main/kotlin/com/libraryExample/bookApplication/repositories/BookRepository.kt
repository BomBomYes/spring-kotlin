package com.libraryExample.bookApplication.repositories

import com.libraryExample.bookApplication.model.Book
import org.springframework.data.jpa.repository.JpaRepository
interface BookRepository : JpaRepository<Book, Long> {
    fun findByTitle(title: String): Book?
    fun findByAuthor(author: String): Book?
    fun findByPublishedDate(publishedDate: String): Book?
}
