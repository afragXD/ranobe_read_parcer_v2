package data

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.varchar
import org.jetbrains.exposed.sql.and

object Authors : IntIdTable("\"Authors\"") {
    val name_en = varchar("name_original", 60)
}

object Tags : IntIdTable("\"Tag\"") {
    val name = varchar("name", 60)
}

object Countries : IntIdTable("\"Countries\"") {
    val name = varchar("name", 60)
}


object Ranobe : IntIdTable("\"Ranobe\"") {
    val name = varchar("name", 100)
    val enName = varchar("en_name", 100).nullable()
    val image = varchar("image", 150).nullable()
    val description = text("description").nullable()
    val rating = decimal("rating", 3, 2).nullable()
    val status = varchar("status", 50).nullable()
    val chapters = integer("chapters").nullable()
    val year = integer("year").nullable().check { it greaterEq 0 }
    val author = reference("author_id", Authors)
    val ratingCount = integer("rating_count").default(0)
    val country = reference("country_id", Countries)
}

object Chapters : IntIdTable("\"Chapter\"") {
    val book = reference("ranobe_id", Ranobe)
    val chapterNumber = integer("chapter_number")
    val chapterName = varchar("chapter_name", 200)
    val chapterText = mediumText("chapter_text")
    init {
        uniqueIndex(book, chapterNumber)
    }
}

object BookGenres : Table("\"RanobeTags\"") {
    val book = reference("ranobe_id", Ranobe)
    val genre = reference("tag_id", Tags)
    override val primaryKey = PrimaryKey(book, genre)
}