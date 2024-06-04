package info

import org.jsoup.nodes.Document

val chaptersSelectorList = listOf(
    "#fs-info > div.r-fullstory-spec > ul:nth-child(1) > li:nth-child(7) > span > span",
    "",
)

fun getChapters(
    document: Document,
    index: Int,
): Int {
    return document.select(chaptersSelectorList[index]).text().toInt()
}