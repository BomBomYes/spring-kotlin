package com.libraryExample.bookApplication.service

import com.libraryExample.bookApplication.datasource.BookDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class BookServiceTest {
    private val dataSource: BookDataSource = mockk(relaxed = true)
    private val bookService = BookService(dataSource)

    @Test
    fun `should call its data source to retrieve books`() {
        // when
        bookService.getBooks()

        // then
        verify(exactly = 1) { dataSource.retrieveBooks() }
    }
}