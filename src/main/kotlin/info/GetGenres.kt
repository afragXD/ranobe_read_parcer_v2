package info

import org.jsoup.nodes.Document

val genresSelectorList = listOf(
    "#mc-fs-genre",
    "",
)

fun getGenres(
    document: Document,
    index: Int,
): Set<String> {
    return document.select(genresSelectorList[index]).text().split(", ").map { it.trim() }.toSet()
}