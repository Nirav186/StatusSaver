package com.nirav.statussaver.ui.features.image

import android.os.Environment
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nirav.statussaver.core.StorageUtils
import com.nirav.statussaver.core.convertTimeForChat
import com.nirav.statussaver.core.isImageFile
import com.nirav.statussaver.core.isVideoFile
import com.nirav.statussaver.data.model.FileMedia
import com.nirav.statussaver.ui.theme.Primary
import java.io.File

@Composable
fun ImagePlayScreen(
    fileMedia: FileMedia
) {

    val context = LocalContext.current
    val imageList = remember {
        mutableStateListOf<FileMedia>()
    }

    LaunchedEffect(key1 = true, block = {
        val docFile = File(
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + "WhatsApp" + File.separator + "Media" + File.separator + ".Statuses"
        )
        if (docFile.exists()) {
            val fileList = docFile.listFiles() as Array<File>
            if (fileList.isNotEmpty()) {
                fileList.forEach { file ->
                    if (!file.name.equals(".nomedia", ignoreCase = true)) {
                        if (isImageFile(file.path)) {
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
                            imageList.add(media)
                        }
                    }
                }
            }
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current).data(fileMedia.path).build(),
            contentDescription = ""
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
                    .padding(2.dp),
                imageVector = Icons.Default.Download,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}