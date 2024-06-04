package info

import org.jsoup.nodes.Document

val statusSelectorList = listOf(
    "#fs-info > div.r-fullstory-spec > ul:nth-child(1) > li:nth-child(4) > span > a",
    "",
)

fun getStatus(
    document: Document,
    index: Int,
): String {
    return document.select(statusSelectorList[index]).text()
}