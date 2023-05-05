package com.nirav.statussaver.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.nirav.statussaver.R
import com.nirav.statussaver.data.model.FileMedia
import com.nirav.statussaver.ui.theme.Primary

@Composable
fun StatusImageCard(item: FileMedia, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
            .border(
                width = 2.dp,
                shape = CircleShape,
                color = Primary
            )
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.path)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StatusVideoCard(item: FileMedia, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .border(
                width = 2.dp,
                shape = CircleShape,
                color = Color(0xFF25d366)
            )
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .padding(3.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            model = item.path,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF000000).copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .size(16.dp),
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "",
                colorFilter = ColorFilter.tint(color = Color.White.copy(0.65f))
            )
        }
    }
}