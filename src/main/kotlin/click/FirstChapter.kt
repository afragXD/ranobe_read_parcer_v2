package click

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


val firstChapterSelectorList = listOf(
    "#dle-content > article > main > div.str_left > div > div.r-fullstory-s1 > div.r-fullstory-poster > div.r-fullstory-btns > a.btn.read-begin",
    "-",
)

val urlFirstChapter = listOf(
    ""
)

fun firstChapter(
    document: Document,
    index: Int,
    client: HttpClient,
): Document{

    val document1:Document

    if (index!=1) {
        var url = document.select(firstChapterSelectorList[index]).attr("href")
        if (!url.startsWith("https")){
            url = "https://ranobes.com/$url"
        }
        //url = "https://ranobes.com/$url"
        //println(url)
        runBlocking {
            // Выполняем запрос и получаем HTML контент страницы
            val response: HttpResponse = client.get(url)
            val responseBody: String = response.bodyAsText()

            // Парсим HTML с помощью Jsoup
            document1 = Jsoup.parse(responseBody)
        }
    }else{
        runBlocking {
            // Выполняем запрос и получаем HTML контент страницы
            val response: HttpResponse = client.get(urlFirstChapter[0])
            val responseBody: String = response.bodyAsText()

            // Парсим HTML с помощью Jsoup
            document1 = Jsoup.parse(responseBody)
        }

        //driver.get(urlFirstChapter[0])
    }
    return document1
}