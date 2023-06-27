package com.nirav.statussaver.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.nirav.statussaver.navigation.buildImagePlayRoute
import com.nirav.statussaver.navigation.buildVideoPlayRoute
import com.nirav.statussaver.ui.features.downloads.DownloadsScreen
import com.nirav.statussaver.ui.features.home.HomeScreen
import com.nirav.statussaver.ui.features.settings.SettingScreen

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomNavGraph(
    rootNavController: NavHostController,
    pagerState: PagerState
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            count = 3
        ) { page ->
            when (page) {
                0 -> HomeScreen(
                    onImageClick = {
                        rootNavController.navigate(buildImagePlayRoute(it))
                    },
                    onVideoClick = {
                        rootNavController.navigate(buildVideoPlayRoute(it))
                    }
                )
                1 -> DownloadsScreen(
                    onImageClick = {
                        rootNavController.navigate(buildImagePlayRoute(it))
                    },
                    onVideoClick = {
                        rootNavController.navigate(buildVideoPlayRoute(it))
                    }
                )
                2 -> SettingScreen()
            }
        }
    }
}