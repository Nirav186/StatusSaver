package com.nirav.statussaver.navigation

import com.nirav.statussaver.R
import com.nirav.statussaver.core.Constants

sealed class BottomNavigationItem(var title: String, var icon: Int, var route: String) {
    object HOME : BottomNavigationItem(
        title = "Home",
        icon = R.drawable.ic_home,
        route = Constants.BottomNavigationItemRoute.HOME
    )

    object DOWNLOADS : BottomNavigationItem(
        title = "Downloads",
        icon = R.drawable.ic_downloads,
        route = Constants.BottomNavigationItemRoute.DOWNLOADS
    )

    object SETTINGS : BottomNavigationItem(
        title = "Settings",
        icon = R.drawable.ic_setting,
        route = Constants.BottomNavigationItemRoute.SETTINGS
    )
}



