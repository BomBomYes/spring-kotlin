import com.tdmnsExamlpe.bookApplication.controller.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.tdmnsExamlpe.bankApplication.model.Book
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest @Autowired constructor(
    val mockMvc: MockMvc
) {
    @MockBean
    lateinit var objectMapper: ObjectMapper
    private val baseUrl = "/api/books"

    @Nested
    @DisplayName("GET /api/books")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBooks {

        @Test
        fun `should return all books`() {
            // when / then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].id") {
                        value(1)
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
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.id") {
                        value(id)
                    }
                }
        }

        @Test
        fun `should return NOT FOUND if the book id doesnt exist`() {
            // given
            val id = 99999

            // when / then
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /api/books")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddBook {

        @Test
        fun `should add the new book`() {
            // given
            val newBook = Book(0, "New Book", "New Author", "Great Book")
            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBook)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        jsonPath("$.title") {
                            value(newBook.title)
                        }
                    }
                }
        }

        @Test
        fun `should return BAD REQUEST if book with given id already exist`() {
            // given
            val invalidBook = Book(1, "Invalid Book", "Invalid Author", "Invalid review")

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBook)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }

        }
    }

    @Nested
    @DisplayName("PATCH /api/books")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBook {

        @Test
        fun `should update an existing book`() {
            // given
            val updatedBook = Book(1, "Updated Book", "Updated Author", "Updated review")

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBook)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        jsonPath("$.title") {
                            value(updatedBook.title)
                        }
                    }
                }
        }

        @Test
        fun `should return NOT FOUND if no book with given id exist`() {
            // given
            val invalidBook = Book(99999, "Invalid Book", "Invalid Author", "Invalid review")

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBook)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/books/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBook {

        @Test
        @DirtiesContext
        fun `should delete an existing book`() {
            // given
            val id = 1

            // when / then
            mockMvc.delete("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("$baseUrl/$id")
                .andExpect {
                    status { isNotFound() }
                }
        }

        @Test
        fun `should return NOT FOUND if no book with given id exist`() {
            // given
            val invalidId = 99999

            // when / then
            mockMvc.delete("$baseUrl/$invalidId")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}
