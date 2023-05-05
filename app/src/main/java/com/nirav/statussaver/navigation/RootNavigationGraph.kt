package com.nirav.statussaver.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.GsonBuilder
import com.nirav.statussaver.core.UriDeserializer
import com.nirav.statussaver.core.composableWithArgs
import com.nirav.statussaver.core.getString
import com.nirav.statussaver.data.model.FileMedia
import com.nirav.statussaver.test.MainScreen
import com.nirav.statussaver.ui.features.image.ImagePlayScreen
import com.nirav.statussaver.ui.features.video.VideoPlayScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            MainScreen(rootNavController = navController)
        }
        composableWithArgs(route = Screen.VideoPlayScreen.route, "fileMedia") {
            val gson = GsonBuilder()
                .registerTypeAdapter(Uri::class.java, UriDeserializer())
                .create()
            gson.fromJson(it.getString("fileMedia"), FileMedia::class.java)?.let { fileMedia ->
                VideoPlayScreen(fileMedia = fileMedia)
            }
        }
        composableWithArgs(route = Screen.ImagePlayScreen.route, "fileMedia") {
            val gson = GsonBuilder()
                .registerTypeAdapter(Uri::class.java, UriDeserializer())
                .create()
            gson.fromJson(it.getString("fileMedia"), FileMedia::class.java)?.let { fileMedia ->
                ImagePlayScreen(fileMedia = fileMedia)
            }
        }
    }
}