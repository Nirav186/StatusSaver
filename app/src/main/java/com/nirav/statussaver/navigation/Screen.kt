package com.nirav.statussaver.navigation

import android.net.Uri
import com.google.gson.GsonBuilder
import com.nirav.statussaver.core.UriSerializer
import com.nirav.statussaver.data.model.FileMedia


/**
 * Screens for navigation
 */
sealed class Screen(val route: String) {
    object HomeScreen : Screen("app://homeScreen")
    object VideoPlayScreen : Screen("app://videoPlayScreen?fileMedia={fileMedia}")
    object ImagePlayScreen : Screen("app://imagePlayScreen?fileMedia={fileMedia}")
}

fun buildImagePlayRoute(fileMedia: FileMedia): String {
    val gson = GsonBuilder()
        .registerTypeAdapter(Uri::class.java, UriSerializer())
        .create()
    return Uri.Builder()
        .scheme("app")
        .authority("imagePlayScreen")
        .appendQueryParameter("fileMedia", gson.toJson(fileMedia))
        .build()
        .toString()
}

fun buildVideoPlayRoute(fileMedia: FileMedia): String {
    val gson = GsonBuilder()
        .registerTypeAdapter(Uri::class.java, UriSerializer())
        .create()
    return Uri.Builder()
        .scheme("app")
        .authority("videoPlayScreen")
        .appendQueryParameter("fileMedia", gson.toJson(fileMedia))
        .build()
        .toString()
}