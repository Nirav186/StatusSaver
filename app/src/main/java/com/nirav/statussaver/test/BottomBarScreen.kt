package com.nirav.statussaver.test

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.nirav.statussaver.core.Constants

sealed class BottomBarScreen(
    val title: String,
    val icon: ImageVector,
    val description: String,
    val page: Int
) {
    object Home : BottomBarScreen(
        title = "Home",
        icon = Icons.Default.Home,
        description = Constants.BottomNavigationItemRoute.HOME,
        page = 0
    )

    object Downloads : BottomBarScreen(
        title = "Downloads",
        icon = Icons.Default.Download,
        description = Constants.BottomNavigationItemRoute.DOWNLOADS,
        page = 1
    )

    object Settings : BottomBarScreen(
        title = "Settings",
        icon = Icons.Default.Settings,
        description = Constants.BottomNavigationItemRoute.SETTINGS,
        page = 2
    )
}