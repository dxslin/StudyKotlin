/**
 * author: slin
 * date: 2020/9/30
 * description:
 *
 */


fun String?.or(another: String): String {
    return if (isNullOrBlank()) another else this!!
}
