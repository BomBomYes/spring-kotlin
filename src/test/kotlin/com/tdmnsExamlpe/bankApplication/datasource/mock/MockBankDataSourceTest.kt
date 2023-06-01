package com.tdmnsExamlpe.bookApplication.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MockBookDataSourceTest {

    private val mockDataSource = MockBookDataSource()

    @Test
    fun `should provide a collection of books`() {
        // when
        val books = mockDataSource.retrieveBooks()

        // then
        assertThat(books.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        // when
        val books = mockDataSource.retrieveBooks()

        // then
        assertThat(books).allMatch { it.title.isNotBlank() }
        assertThat(books).allMatch { it.author.isNotBlank() }
        assertThat(books).allMatch { it.review.isNotBlank() }
    }
}
