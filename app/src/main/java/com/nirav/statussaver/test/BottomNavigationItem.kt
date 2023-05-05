package com.nirav.statussaver.test

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.nirav.statussaver.core.Constants

sealed class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
) {
    object HOME : BottomNavItem(
        name = "Home",
        icon = Icons.Default.Home,
        route = Constants.BottomNavigationItemRoute.HOME
    )

    object DOWNLOADS : BottomNavItem(
        name = "Downloads",
        icon = Icons.Default.Download,
        route = Constants.BottomNavigationItemRoute.DOWNLOADS
    )

    object SETTINGS : BottomNavItem(
        name = "Settings",
        icon = Icons.Default.Settings,
        route = Constants.BottomNavigationItemRoute.SETTINGS
    )
}