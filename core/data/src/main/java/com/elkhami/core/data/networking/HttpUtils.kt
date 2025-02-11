package com.elkhami.core.data.networking

import java.net.URLDecoder

fun extractLastPageNumber(linkHeader: String?): Int? {
    return linkHeader?.split(",")
        ?.firstOrNull { it.contains("rel=\"last\"") }
        ?.let { extractPageNumberFromLink(it) }
}

private fun extractPageNumberFromLink(link: String): Int? {
    return try {
        val url = link.substringBefore(";").trim().removeSurrounding("<", ">")
        val query = URLDecoder.decode(url.substringAfter("?"), "UTF-8")
        query.split("&")
            .firstOrNull { it.startsWith("page=") }
            ?.substringAfter("page=")
            ?.toIntOrNull()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}