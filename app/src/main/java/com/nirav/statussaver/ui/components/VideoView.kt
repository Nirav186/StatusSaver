package com.nirav.statussaver.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun VideoView(videoUri: String) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            exoPlayer.setMediaItem(MediaItem.fromUri(videoUri))
            exoPlayer.prepare()
        }

//    DisposableEffect(
//        AndroidView(factory = {
////            StyledPlayerView(context).apply {
////                player = exoPlayer
////            }
//        })
//    ) {
//        onDispose { exoPlayer.release() }
//    }
}