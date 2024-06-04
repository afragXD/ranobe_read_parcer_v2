import click.firstChapter
import click.firstChapterSelectorList
import data.*
import info.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


// список с главными страницами произведений
val url = listOf(
    //"https://ranobes.com/ranobe/5951-a-will-eternal.html",
    //"https://ranobes.com/ranobe/305-i-shall-seal-the-heavens.html"
    //"https://ranobelib.me/omniscient-readers-viewpoint-novel?section=info"

    "https://ranobes.com/ranobe/213514-ellen-no-nikki.html"

    //"https://ranobes.com/ranobe/347562-combat-continent.html"
    //"https://ranobes.com/ranobe/287925-never-die-twice.html"
    ////////"https://ranobes.com/ranobe/242439-you-called-on-the-wrong.html"

    //"https://ranobes.com/ranobe/5951-a-will-eternal.html"
)

// принимает ссылку на главную страницу произведения
// возвращает номер сайта с которого парсится это произведение
fun getSiteRank(link: String): Int {
    val ranobeLibPattern = "ranobelib".toRegex()
    val ranobeHubPattern = "ranobehub".toRegex()
    val ranobeSPattern = "ranobes".toRegex()

    return when {
        ranobeHubPattern.containsMatchIn(link) -> 2
        ranobeLibPattern.containsMatchIn(link) -> 1
        ranobeSPattern.containsMatchIn(link) -> 0
        else -> -1 // Возвращаем -1 для несоответствующих ссылок
    }
}

val nextChapterSelectorList = listOf(
    "#next",
    "-",
)


fun main() = runBlocking {

    val url = url[0]
    val urlIndex = getSiteRank(url)

    // Создаем клиент Ktor
    val client = HttpClient(CIO)

    try {
        // Выполняем запрос и получаем HTML контент страницы
        val response: HttpResponse = client.get(url)
        val responseBody: String = response.bodyAsText()

        // Парсим HTML с помощью Jsoup
        var document: Document = Jsoup.parse(responseBody)

        //println(getName(document, urlIndex))
        //println(getEnName(document, urlIndex))
        //println(getImg(document, urlIndex))
        //println(getDescription(document, urlIndex))
        //println(getRating(document, urlIndex))
        //println(getStatus(document, urlIndex))
        //println(getChapters(document, urlIndex))
        //println(getYear(document, urlIndex))
        //println(getAuthor(document, urlIndex))
        //println(getRatingCount(document, urlIndex))
        //println(getGenres(document, urlIndex))
        //println(getCountry(document, urlIndex))


        val bookDTO = BookDTO(
            name = getName(document, urlIndex),
            enName = getEnName(document, urlIndex),
            image = getImg(document, urlIndex),
            descript = getDescription(document, urlIndex),
            rating = getRating(document, urlIndex),
            status = getStatus(document, urlIndex),
            chapters = getChapters(document, urlIndex),
            year = getYear(document, urlIndex),
            authorName = getAuthor(document, urlIndex),
            ratingCount = getRatingCount(document, urlIndex),
            //translator = getTranslator(document, urlIndex),
            genres = getGenres(document, urlIndex),
            countryName = getCountry(document, urlIndex)
        )
        println(bookDTO)

        //val bookId = addBook(bookDTO)

        document = firstChapter(document, urlIndex, client)


        //println(getName(document, urlIndex))
        //println(getEnName(document, urlIndex))
        //println(getImg(document, urlIndex))
        //println(getDescription(document, urlIndex))
        //println(getRating(document, urlIndex))
        //println(getStatus(document, urlIndex))
        //println(getChapters(document, urlIndex))
        //println(getYear(document, urlIndex))
        //println(getAuthor(document, urlIndex))
        //println(getRatingCount(document, urlIndex))
        //println(getGenres(document, urlIndex))
        //println(getCountry(document, urlIndex))

        for (i in 1..bookDTO.chapters){
            val chapterDTO = ChapterDTO(
                chapterNumber = i,
                chapterName = getChapterName(document, urlIndex),
                bookId = 1,//bookId,
                chapterText = getChapterText(document, urlIndex)
            )

            println(chapterDTO)
            //addChapter(chapterDTO)

            try {
                var url = document.select(nextChapterSelectorList[urlIndex]).attr("href")
                //url = "https://ranobes.com/$url"
                //println(url)
                runBlocking {
                    // Выполняем запрос и получаем HTML контент страницы
                    val response: HttpResponse = client.get(url)
                    val responseBody: String = response.bodyAsText()

                    // Парсим HTML с помощью Jsoup
                    document = Jsoup.parse(responseBody)
                }
            }catch (e: Exception){
                client.close()
                break
            }
        }

    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        // Закрываем клиент
        client.close()
    }

}


fun addBook(bookDTO: BookDTO): Int {
    var book_id = 0

    Database.connect(
        url = "jdbc:mariadb://192.168.0.152:3306/ranobe_read",
        driver = "org.mariadb.jdbc.Driver",
        user = "arisen",
        password = System.getenv("maria_pass")
    )

    transaction {
        // Получаем или создаем автора
        val author_id = Authors.select { Authors.name eq bookDTO.authorName }
            .map { it[Authors.id].value }
            .firstOrNull() ?: Authors.insertAndGetId {
            it[name] = bookDTO.authorName
        }.value

        // Получаем или создаем страну
        val country_id = Countries.select { Countries.name eq bookDTO.countryName }
            .map { it[Countries.id].value }
            .firstOrNull() ?: Countries.insertAndGetId {
            it[name] = bookDTO.countryName
        }.value


        // Вставляем книгу
        book_id = Books.insertAndGetId {
            it[name] = bookDTO.name
            it[enName] = bookDTO.enName
            it[image] = bookDTO.image
            it[descript] = bookDTO.descript
            it[rating] = bookDTO.rating
            it[status] = bookDTO.status
            it[chapters] = bookDTO.chapters
            it[year] = bookDTO.year
            it[author] = author_id
            it[country] = country_id
            it[ratingCount] = bookDTO.ratingCount
        }.value

        // Вставляем жанры книги
        bookDTO.genres.forEach { genreName ->
            val genre_id = Genres.select { Genres.name eq genreName }
                .map { it[Genres.id].value }
                .firstOrNull() ?: Genres.insertAndGetId {
                it[name] = genreName
            }.value
            BookGenres.insert {
                it[book] = book_id
                it[genre] = genre_id
            }
        }
    }
    return book_id
}

fun addChapter(chapterDTO: ChapterDTO){
    transaction{
        Chapters.insert {
            it[book] = chapterDTO.bookId
            it[chapterNumber] = chapterDTO.chapterNumber
            it[chapterName] = chapterDTO.chapterName
            it[chapterText] = chapterDTO.chapterText
        }
    }
}