package com.tdmnsExamlpe.bankApplication.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tdmnsExamlpe.bankApplication.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    private val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks`() {
            // when / then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") {
                        value("Q2R2")
                    }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return single bank with the given account number`() {
            // given
            val accountNumber = "Q2R2"

            // when / then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") {
                        value(accountNumber)
                    }
                }
        }

        @Test
        fun `should return NOT FOUND if the account number doesnt exist`() {
            // given
            val accountNumber = "does_not_exist"

            // when / then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddBank {

        @Test
        fun `should add the new bank`() {
            // given
            val accountNumber = "Z4F8"
            val trust = 5.0
            val transactionFee = 2
            val newBank = Bank(accountNumber = accountNumber, trust, transactionFee)
            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect {
                    content { json(objectMapper.writeValueAsString(newBank)) }
                }
        } 
        
        @Test
        fun `should return BAD REQUEST if bank with given number already exist`() {
            // given
            val invalidBank = Bank("Q2R2", 1.0, 1)
            
            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            
            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
            
        } 
    }
    
    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
     
        @Test
        fun `should update an existing bank`() {
            // given
            val updatedBank = Bank("PQ03", 1.0, 1)
            
            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            
            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }
        } 
        
        @Test
        fun `should return BAD REQUEST if no bank with given account number exist`() {
            // given
            val invalidBank = Bank("does_not_exist", 1.0, 1)

            // when
            val performPost = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        } 
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {

        @Test
        @DirtiesContext // if you want change order for tests (f.e.) move delete above GET request
        fun `should delete an existing bank`() {
            // given
            val accountNumber = "PQ03"

            // when / then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect {
                    status { isNotFound() }
                }
        }

        @Test
        fun `should return NOT FOUND if no bank with given account number exist`() {
            // given
            val invalidAccountNumber = "does_not_exist"

            // when / then
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}