package com.nirav.statussaver.ui.features.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.storage.StorageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nirav.statussaver.ui.components.CustomTabLayout
import com.nirav.statussaver.ui.components.StatusImageCard
import com.nirav.statussaver.ui.components.StatusVideoCard
import com.nirav.statussaver.data.model.FileMedia
import com.nirav.statussaver.ui.theme.BackgroundColor
import com.nirav.statussaver.ui.theme.Primary
import com.nirav.statussaver.ui.theme.ProfileImageShadowColor

@Composable
fun HomeScreen(onImageClick: (FileMedia) -> Unit, onVideoClick: (FileMedia) -> Unit) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()

    LaunchedEffect(key1 = true, block = {
        if (viewModel.imageList.isEmpty()) {
            viewModel.getStatuses(context)
        }else{
            viewModel.setData()
        }
    })

    when (val uiState = viewModel.uiState.collectAsState().value) {
        is HomeUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is HomeUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColor)
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = "WhatsApp Status Saver",
                    style = TextStyle(
                        color = Primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                CustomTabLayout(viewModel.selectedTabPos.value) {
                    viewModel.selectedTabPos.value = it
                }
                if (viewModel.selectedTabPos.value == 0) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(viewModel.imageList.size) { pos ->
                            val item = viewModel.imageList[pos]
                            StatusImageCard(item) {
                                onImageClick(item)
                            }
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(viewModel.videoList.size) { pos ->
                            val item = viewModel.videoList[pos]
                            StatusVideoCard(item) {
                                onVideoClick(item)
                            }
                        }
                    }
                }
            }
            Log.e("TAG111", "HomeScreen: " + uiState.imageList.size)
        }

        is HomeUiState.Permission -> {
            PermissionLayout()
        }
    }
}

@Composable
fun PermissionLayout() {
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.let { data ->
                viewModel.onPermissionGranted(data, context)
            }
        }

    val direcoty = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
    val smb = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
    var intent: Intent? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        intent = smb.primaryStorageVolume.createOpenDocumentTreeIntent()
    }
    var uri = intent!!.getParcelableExtra<Uri>("android.provider.extra.INITIAL_URI")
    val scheme = uri.toString().replace("/root/", "/document/")
    uri = Uri.parse("$scheme%3A$direcoty")
    intent.putExtra("android.provider.extra.INITIAL_URI", uri)

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                launcher.launch(intent)
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = ProfileImageShadowColor)
        ) {
            Text(
                text = "Allow",
                style = TextStyle(
                    color = Color.White, fontSize = 16.sp
                )
            )
        }
    }
}
