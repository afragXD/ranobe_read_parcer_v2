package info

import org.jsoup.nodes.Document


val authorSelectorList = listOf(
    "#fs-info > div.r-fullstory-spec > ul:nth-child(1) > li:nth-child(10) > span > a",
    "",
)

fun getAuthor(
    document: Document,
    index: Int,
): String {
    return document.select(authorSelectorList[index]).text()
}