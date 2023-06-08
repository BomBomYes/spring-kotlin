package com.libraryExample.bookApplication.datasource.mock

import org.assertj.core.api.Assertions.assertThat

class MockBookDataSourceTest {
    private val mockDataSource = MockBookDataSource()

    @org.junit.jupiter.api.Test
    fun `should provide a collection of books`() {
        // when
        val books = mockDataSource.retrieveBooks()
        // then
        assertThat(books.size).isGreaterThanOrEqualTo(3)
    }

    @org.junit.jupiter.api.Test
    fun `should provide some mock data`() {
        // when
        val books = mockDataSource.retrieveBooks()
        // then
        assertThat(books).allMatch { it.title.isNotBlank() }
        assertThat(books).anyMatch { it.author.isNotBlank() }
        assertThat(books).anyMatch { it.review.isNotBlank() }
    }
}