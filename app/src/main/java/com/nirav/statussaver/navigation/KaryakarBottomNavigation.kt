package com.nirav.statussaver.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nirav.statussaver.ui.theme.Secondary

@Composable
fun KaryakarBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.HOME,
        BottomNavigationItem.DOWNLOADS,
        BottomNavigationItem.SETTINGS
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        style = TextStyle(letterSpacing = 1.8.sp),
                        maxLines = 1
                    )
                },
                selectedContentColor = Secondary,
                unselectedContentColor = White,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
//                    if (currentRoute != item.route) {
//                        navController.navigate(item.route)
//                    }
//                    navController.navigate(item.route) {
//                        navController.graph.startDestinationRoute?.let { route ->
//                            popUpTo(route) {
//                                saveState = true
//                            }
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun KaryakarBottomNavigationPreview() {
    KaryakarBottomNavigation(
        navController = rememberNavController()
    )
}