package info

import org.jsoup.nodes.Document


val authorSelectorList = listOf(
    "#fs-info > div.r-fullstory-spec > ul:nth-child(1) > li:nth-child(10) > span > a",
    "#app > div.page > div.ia_h.ia_ib.fade.container > div.pb_e > div > div:nth-child(7) > div.va_c6 > a",
)

fun getAuthor(
    document: Document,
    index: Int,
): String {
    return document.select(authorSelectorList[index]).text()
}

