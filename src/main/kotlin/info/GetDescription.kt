package info

import org.jsoup.nodes.Document

val descriptionSelectorList = listOf(
    "#fs-info > div.r-desription.showcont > div > div",
    "",
)


fun getDescription(
    document: Document,
    index: Int,
): String {
    val element = document.select(descriptionSelectorList[index])
    //val description = Jsoup.parse(element)
    //element.select("style").remove()
    //return element.html()
    return element.text()
}