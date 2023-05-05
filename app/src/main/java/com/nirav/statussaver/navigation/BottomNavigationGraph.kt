package com.nirav.statussaver.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nirav.statussaver.core.Constants
import com.nirav.statussaver.ui.features.downloads.DownloadsScreen
import com.nirav.statussaver.ui.features.home.HomeScreen
import com.nirav.statussaver.ui.features.settings.SettingScreen

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
    rootNavController: NavController
) {
    NavHost(
        navController = navController,
        startDestination = Constants.BottomNavigationItemRoute.HOME
    ) {
        composable(BottomNavigationItem.HOME.route) {
            HomeScreen(
                onImageClick = {
                    rootNavController.navigate(buildImagePlayRoute(it))
                },
                onVideoClick = {
                    rootNavController.navigate(buildVideoPlayRoute(it))
                }
            )
        }
        composable(BottomNavigationItem.DOWNLOADS.route) {
            DownloadsScreen()
        }
        composable(BottomNavigationItem.SETTINGS.route) {
            SettingScreen()
        }
    }
}