package info

import org.jsoup.nodes.Document
import java.time.Duration

val chapterNameSelectorList = listOf(
    "#dle-content > article > div.block.story.shortstory > h1",
    "",
)

fun getChapterName(
    document: Document,
    index: Int,
): String {
    return document.select(chapterNameSelectorList[index]).text()
}