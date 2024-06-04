package info

import org.jsoup.nodes.Document

val imgSelectorList = listOf(
    "#dle-content > article > main > div.str_left > div > div.r-fullstory-s1 > div.r-fullstory-poster > div.poster > a",
    "",
)

fun getImg(
    document: Document,
    index: Int,
): String {
    return document.select(imgSelectorList[index]).attr("href")
}