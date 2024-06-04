package info

import org.jsoup.nodes.Document
import java.time.Duration

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