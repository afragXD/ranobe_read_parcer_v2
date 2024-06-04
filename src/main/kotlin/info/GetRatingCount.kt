package info

import org.jsoup.nodes.Document


val ratingCountSelectorList = listOf(
    "#rate_b > div > div > div > span.small.grey",
    "",
)
// #vote-num-id-213514
// #vote-num-id-5951

fun getRatingCount(
    document: Document,
    index: Int,
): Int {
    //println(document.select(ratingCountSelectorList[index]).select("span")[1].attr("content"))
    //return document.select(ratingCountSelectorList[index]).text().toInt()
    return document.select(ratingCountSelectorList[index]).select("span")[1].attr("content").toInt()
}