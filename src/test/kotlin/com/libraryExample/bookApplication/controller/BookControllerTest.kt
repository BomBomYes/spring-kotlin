package com.libraryExample.bookApplication.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.libraryExample.bookApplication.model.Book
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookControllerTest @Autowired constructor(
    val mockMvc: MockMvc, val objectMapper: ObjectMapper
) {
    private val baseUrl = "/api/books"

    @Nested
    @DisplayName("GET /api/books")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBooks {

        @Test
        fun `should return all books`() {
            // when / then
            mockMvc.get(baseUrl).andDo { print() }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].title") {
                    value("The Hobbit")
                }
            }
        }
    }

    @Nested
    @DisplayName("GET /api/books/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBook {

        @Test
        fun `should return single book with the given id`() {
            // given
            val id = 1

            // when / then
            mockMvc.get("$baseUrl/$id").andDo { print() }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.title") {
                    value("The Hobbit")
                }
            }
        }

        @Test
        fun `should return NOT FOUND if the given id does not exist`() {
            // given
            val id = 999

            // when / then
            mockMvc.get("$baseUrl/$id").andDo { print() }.andExpect {
                status { isNotFound() }
            }
        }
    }

    @Nested
    @DisplayName("POST /api/books")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddBook {

        @Test
        fun `should add the new book`() {
            // given
            val id = 4
            val title = "The Great Gatsby"
            val author = "F. Scott Fitzgerald"
            val review = "A masterpiece of American literature"
            val publishedDate = LocalDate.of(1925, 4, 10).toString()
            val newBook = Book().apply {
                this.id = id
                this.title = title
                this.author = author
                this.review = review
                this.publishedDate = publishedDate
                this.coverImage = null
            }

            // when
            val performPost = mockMvc.post("/api/books") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBook)
            }

            // then
            performPost.andDo { print() }.andExpect {
                status { isCreated() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(newBook))
                }
            }

            mockMvc.get("/api/books/${newBook.id}").andExpect {
                content { json(objectMapper.writeValueAsString(newBook)) }
            }
        }

        @Test
        fun `should return BAD REQUEST if book with given id already exist`() {
            // given
            val invalidBook = Book().apply {
                id = 1
                title = "The Hobbit"
                author = "J.R.R. Tolkien"
                review = "A great book"
                publishedDate = LocalDate.of(1937, 9, 21).toString()
                coverImage = null
            }

            // when
            val performPost = mockMvc.post("/api/books") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBook)
            }

            // then
            performPost.andDo { print() }.andExpect {
                status { isBadRequest() }
            }
        }
    }

    @Nested
    @DisplayName("PATCH /api/books")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBook {

        @Test
        fun `should update the book with the given id`() {
            // given
            val id = 1
            val updatedBook = Book().apply {
                this.id = id
                title = "The Hobbit"
                author = "J.R.R. Tolkien"
                review = "A great book"
                publishedDate = LocalDate.of(1937, 9, 21).toString()
                coverImage = null
            }

            // when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBook)
            }

            // then
            performPatchRequest.andDo { print() }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(updatedBook))
                }
            }

            mockMvc.get("/api/books/$id").andExpect {
                content { json(objectMapper.writeValueAsString(updatedBook)) }
            }
        }

        @Test
        fun `should return NOT FOUND if the given id does not exist`() {
            // given
            val id = 999
            val updatedBook = Book().apply {
                this.id = id
                title = "The Hobbit"
                author = "J.R.R. Tolkien"
                review = "A great book"
                publishedDate = LocalDate.of(1937, 9, 21).toString()
                coverImage = null
            }

            // when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBook)
            }

            // then
            performPatchRequest.andDo { print() }.andExpect {
                status { isNotFound() }
            }
        }
    }

    @Nested
    @DisplayName("DELETE /api/books/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBook {

        @Test
        fun `should delete the book with the given id`() {
            // given
            val id = 1

            // when
            val performDeleteRequest = mockMvc.delete("$baseUrl/$id")

            // then
            performDeleteRequest.andDo { print() }.andExpect {
                status { isNoContent() }
            }

            mockMvc.get("$baseUrl/$id").andExpect {
                status { isNotFound() }
            }
        }

        @Test
        fun `should return NOT FOUND if the given id does not exist`() {
            // given
            val id = 999

            // when
            val performDeleteRequest = mockMvc.delete("$baseUrl/$id")

            // then
            performDeleteRequest.andDo { print() }.andExpect {
                status { isNotFound() }
            }
        }
    }
}
