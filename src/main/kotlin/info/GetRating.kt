package info

import org.jsoup.nodes.Document
import java.math.BigDecimal

val ratingSelectorList = listOf(
    "#rate_b > div > div > div > span.bold",
    "",
)

fun getRating(
    document: Document,
    index: Int,
): BigDecimal {
    var rating = document.select(ratingSelectorList[index]).text().toFloat()
    if (index==0)
        rating *= 2
    return rating.toBigDecimal()
}