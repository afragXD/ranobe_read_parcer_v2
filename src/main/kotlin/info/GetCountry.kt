package info

import org.jsoup.nodes.Document

val countrySelectorList = listOf(
    "#fs-info > div.r-fullstory-spec > ul:nth-child(1) > li:nth-child(9) > span > a",
    "",
)

fun getCountry(
    document: Document,
    index: Int,
): String {
    val country = document.select(countrySelectorList[index]).text()
    if (index==0) {
        return when (country) {
            "Китайский" -> "Китай"
            "Корейский" -> "Корея"
            "Японский" -> "Япония"
            "Английский" -> "Англия"
            "Русский" -> "Россия"
            else -> "Китай"
        }
    }
    return country
}