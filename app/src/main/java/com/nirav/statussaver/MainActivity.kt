package com.nirav.statussaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.nirav.statussaver.navigation.RootNavigationGraph
import com.nirav.statussaver.ui.theme.StatusSaverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatusSaverTheme {
                val navController = rememberNavController()
                RootNavigationGraph(navController)
            }
        }
    }
}