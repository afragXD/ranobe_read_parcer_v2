package data

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.varchar
import org.jetbrains.exposed.sql.and

object Authors : IntIdTable("authors") {
    val name = varchar("name", 60)
}

object Genres : IntIdTable("genres") {
    val name = varchar("name", 60)
}

object Countries : IntIdTable("countries") {
    val name = varchar("name", 60)
}


object Books : IntIdTable("books") {
    val name = varchar("name", 100)
    val enName = varchar("en_name", 100).nullable()
    val image = varchar("image", 150).nullable()
    val descript = text("descript").nullable()
    val rating = decimal("rating", 3, 2).nullable()
    val status = varchar("status", 50).nullable()
    val chapters = integer("chapters").nullable()
    val year = integer("year").nullable().check { it greaterEq 0 }
    val author = reference("author_id", Authors)
    val ratingCount = integer("rating_count").default(0)
    val country = reference("country_id", Countries)
}

object Chapters : IntIdTable("chapters") {
    val book = reference("book_id", Books)
    val chapterNumber = integer("chapter_number")
    val chapterName = varchar("chapter_name", 200)
    val chapterText = mediumText("chapter_text")
    init {
        uniqueIndex(book, chapterNumber)
    }
}

object BookGenres : Table("book_genres") {
    val book = reference("book_id", Books)
    val genre = reference("genre_id", Genres)
    override val primaryKey = PrimaryKey(book, genre)
}