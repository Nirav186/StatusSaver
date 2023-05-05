package com.nirav.statussaver.core

import java.net.URLConnection
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*


fun isImageFile(path: String?): Boolean {
    val mimeType: String = URLConnection.guessContentTypeFromName(path)
    return mimeType.startsWith("image")
}

fun isVideoFile(path: String?): Boolean {
    val mimeType: String = URLConnection.guessContentTypeFromName(path)
    return mimeType.startsWith("video")
}

fun Long.convertTimeForChat(): String? {
    val date = Date(this)
    val format: Format = SimpleDateFormat("KK:mm a")
    return format.format(date)
}