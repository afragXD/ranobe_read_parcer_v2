package data

import java.math.BigDecimal


data class BookDTO(
    val name: String,
    val enName: String,
    val image: String,
    val descript: String,
    val rating: BigDecimal,
    val status: String,
    val chapters: Int,
    val year: Int?,
    val authorName: String,
    val countryName: String,
    val genres: Set<String>,
    val ratingCount: Int,
    )



data class ChapterDTO(
    val bookId: Int,
    val chapterNumber: Int,
    val chapterName: String,
    val chapterText: String,
    )