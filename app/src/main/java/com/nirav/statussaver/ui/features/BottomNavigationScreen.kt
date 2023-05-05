package com.nirav.statussaver.ui.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nirav.statussaver.navigation.BottomNavigationGraph
import com.nirav.statussaver.navigation.KaryakarBottomNavigation

@ExperimentalMaterialApi
@Composable
fun BottomNavigationScreen(rootNavController: NavController) {
    val navController = rememberNavController()
    BottomNavigationGraph(
        navController = navController,
        rootNavController = rootNavController
    )
    Scaffold(
        bottomBar = { KaryakarBottomNavigation(navController = navController) }
    ){
        Box(modifier = Modifier.padding(it)) {
            BottomNavigationGraph(
                navController = navController,
                rootNavController = rootNavController
            )
        }
    }
}