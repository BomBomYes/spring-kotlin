package com.libraryExample.bookApplication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class BookApplication

fun main(args: Array<String>) {
	runApplication<BookApplication>(*args)
}
