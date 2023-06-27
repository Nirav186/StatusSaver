package com.nirav.statussaver.ui.features.downloads

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.nirav.statussaver.core.Constants
import com.nirav.statussaver.core.StorageUtils
import com.nirav.statussaver.core.convertTimeForChat
import com.nirav.statussaver.core.getCreatedFile
import com.nirav.statussaver.core.isImageFile
import com.nirav.statussaver.core.isVideoFile
import com.nirav.statussaver.data.model.FileMedia
import com.nirav.statussaver.ui.components.StatusImageCard
import java.io.File

@Composable
fun DownloadsScreen(onImageClick: (FileMedia) -> Unit, onVideoClick: (FileMedia) -> Unit) {
    val context = LocalContext.current as Activity
    val mediaList = remember { mutableStateListOf<FileMedia>() }
    LaunchedEffect(key1 = true, block = {
        val docFile = context.getCreatedFile()
        if (docFile.exists()) {
            val fileList = docFile.listFiles() as Array<File>
            if (fileList.isNotEmpty()) {
                fileList.forEach { file ->
                    if (!file.name.equals(".nomedia", ignoreCase = true)) {
                        if (isImageFile(file.path) || isVideoFile(file.path)) {
                            val mimeType: String = StorageUtils.getMimeType(
                                context, FileProvider.getUriForFile(
                                    context, "${context.packageName}.provider", file
                                )
                            )
                            val media = FileMedia(
                                path = file.absolutePath,
                                fileName = file.name,
                                mimeType = mimeType,
                                type = StorageUtils.getTypeConstant(mimeType),
                                createTime = file.lastModified().convertTimeForChat().toString()
                            )
                            mediaList.add(media)
                        }
                    }
                }
            }
        }
    })

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Downloads",
            style = TextStyle(
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(mediaList.size) { pos ->
                val item = mediaList[pos]
                ImageCard(fileMedia = item, title = "") {
                    if (item.type == Constants.TYPE_VIDEO) {
                        onVideoClick(item)
                    } else {
                        onImageClick(item)
                    }
                }
            }
        }
    }
}