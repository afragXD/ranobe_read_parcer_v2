package info

import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.jsoup.nodes.Document

val nameSelectorList = listOf(
    "#dle-content > article > main > div.str_left > div > div.r-fullstory-s1 > h1",
    "",
)

val nameEnSelectorList = listOf(
    "#dle-content > article > main > div.str_left > div > div.r-fullstory-s1 > h1",
    "",
)

fun getName(
    document: Document,
    index: Int,
):String{
    val name = document.select(nameSelectorList[index]).text()
    return name.split("•")[0]
}

fun getEnName(
    document: Document,
    index: Int,
):String{
    var name = document.select(nameEnSelectorList[index]).text()
    if (index==0) {
        name = name.split("•")[0]

        val client = HttpClient()
        runBlocking {
            val httpResponse: HttpResponse = client.get("https://api.mymemory.translated.net/get") {
                parameter("q", name)
                parameter("langpair", "ru|en")
            }
            val sttt: String = httpResponse.body()
            val gson = Gson()
            val map = gson.fromJson(sttt, Map::class.java)
            val responseData = map["responseData"] as Map<String, Any>
            val translatedText = responseData["translatedText"] as String
            name = translatedText.replace(" ", "_").lowercase()
            name = name.replace("'", "")
        }
    }

    return name
}