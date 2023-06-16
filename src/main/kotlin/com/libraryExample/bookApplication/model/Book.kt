package com.libraryExample.bookApplication.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var title = ""

    @Column
    var author = ""

    @Column
    var review = ""

    @Column
    var publishedDate: String = LocalDate.now().toString()

    @Lob
    @Column
    var coverImage: ByteArray? = null
}
