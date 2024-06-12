package info

import org.jsoup.nodes.Document

val chapterTextSelectorList = listOf(
    "#arrticle",
    "",
)

fun getChapterText(
    document: Document,
    index: Int,
): String {
    return document.select(chapterTextSelectorList[index]).text()
}