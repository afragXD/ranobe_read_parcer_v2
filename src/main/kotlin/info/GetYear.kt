package info

import org.jsoup.nodes.Document
import java.time.Duration


val yearSelectorList = listOf(
    "#fs-info > div.r-fullstory-spec > ul:nth-child(1) > li:nth-child(8) > span > a",
    "",
)

fun getYear(
    document: Document,
    index: Int,
): Int {
    return document.select(yearSelectorList[index]).text().toInt()
}