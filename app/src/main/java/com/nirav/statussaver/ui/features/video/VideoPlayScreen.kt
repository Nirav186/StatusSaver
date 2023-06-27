package com.nirav.statussaver.ui.features.video

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView
import com.nirav.statussaver.MainViewModel
import com.nirav.statussaver.core.Constants
import com.nirav.statussaver.core.downloadMultipleData
import com.nirav.statussaver.core.isVideoFile
import com.nirav.statussaver.data.model.FileMedia
import com.nirav.statussaver.ui.theme.Primary

@SuppressLint("WrongConstant")
@Composable
fun VideoPlayScreen(fileMedia: FileMedia) {
    val context = LocalContext.current as Activity
    val viewModel = hiltViewModel<MainViewModel>()
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = true, block = {
        if (Constants.docFile?.exists() == true) {
            val documentFileList = Constants.docFile!!.listFiles()
            if (documentFileList.isNotEmpty()) {
                documentFileList.forEach { documentFile ->
                    if (!documentFile.name.equals(
                            ".nomedia",
                            ignoreCase = true
                        )
                    ) {
                        if (isVideoFile(documentFile.uri.path)) {
                            viewModel.addVideoUri(documentFile.uri)
                        }
                    }
                }
                viewModel.videoUris.value.find { it == fileMedia.uri }?.let {
                    viewModel.playVideo(it)
                }
            }
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = viewModel.player
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                    }

                    else -> Unit
                }
            },
            modifier = Modifier
                .fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(14.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(2.dp),
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(2.dp)
                    .clickable(onClick = {
                        context.downloadMultipleData(listOf(fileMedia))
                    }),
                imageVector = Icons.Default.Download,
                contentDescription = null,
                tint = Color.White
            )
        }
    }

}