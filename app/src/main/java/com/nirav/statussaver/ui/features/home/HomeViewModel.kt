package com.nirav.statussaver.ui.features.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nirav.statussaver.core.*
import com.nirav.statussaver.core.Constants.docFile
import com.nirav.statussaver.data.model.FileMedia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private var _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    var selectedTabPos = mutableStateOf(0)

    var imageList = arrayListOf<FileMedia>()
    var videoList = arrayListOf<FileMedia>()

    fun setData(){
        _uiState.value = HomeUiState.Success(imageList)
    }

    fun getStatuses(context: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            if (SharedPrefs.isPermissionGranted) {
                docFile = DocumentFile.fromTreeUri(
                    context, Uri.parse(
                        SharedPrefs.documentUri
                    )
                )
                getStatusesAboveQ(context)
            } else {
                _uiState.value = HomeUiState.Permission
            }
        } else {
            getStatusesBelowQ(context)
        }
    }

    private fun getStatusesBelowQ(context: Context) {
        viewModelScope.launch {
            imageList.clear()
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
                                        context,
                                        "${context.packageName}.provider",
                                        file
                                    )
                                )
                                val media = FileMedia(
                                    path = file.absolutePath,
                                    fileName = file.name,
                                    mimeType = mimeType,
                                    type = StorageUtils.getTypeConstant(mimeType),
                                    createTime = file.lastModified().convertTimeForChat()
                                        .toString()
                                )
                                imageList.add(media)
                            } else if (isVideoFile(file.path)) {
//                                    mediaList.add(file.path)
                                val mimeType: String = StorageUtils.getMimeType(
                                    context, FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.provider",
                                        file
                                    )
                                )
                                val media = FileMedia(
                                    path = file.path,
                                    fileName = file.name,
                                    mimeType = mimeType,
                                    type = StorageUtils.getTypeConstant(mimeType),
                                    createTime = file.lastModified()
                                        .convertTimeForChat().toString()
                                )
                                videoList.add(media)
                            }
                        }
                    }
                }
            }
            _uiState.value = HomeUiState.Success(imageList = imageList)
        }
    }

    private fun getStatusesAboveQ(context: Context) {
        imageList.clear()
        val documentFileList: Array<DocumentFile>
//        docFile = DocumentFile.fromTreeUri(
//            context, Uri.parse(SharedPrefs.documentUri)
//        )
        if (docFile?.exists() == true) {
            documentFileList = docFile!!.listFiles()
            if (documentFileList.isNotEmpty()) {
                documentFileList.forEach { documentFile ->
                    if (!documentFile.name.equals(".nomedia", ignoreCase = true)) {
                        if (isImageFile(documentFile.uri.path)) {
                            val mimeType: String = StorageUtils.getMimeType(
                                context, FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    File(documentFile.uri.path.toString())
                                )
                            )
                            val media = FileMedia(
                                path = documentFile.uri.toString(),
                                fileName = documentFile.name,
                                mimeType = mimeType,
                                type = StorageUtils.getTypeConstant(mimeType),
                                createTime = documentFile.lastModified()
                                    .convertTimeForChat().toString()
                            )
                            imageList.add(media)
                        } else if (isVideoFile(documentFile.uri.path)) {
                            val mimeType: String = StorageUtils.getMimeType(
                                context, FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    File(documentFile.uri.path.toString())
                                )
                            )
                            Log.e("TAG111", "getStatuses: uri==> ${documentFile.uri}")
                            val media = FileMedia(
                                path = documentFile.uri.toString(),
                                fileName = documentFile.name,
                                mimeType = mimeType,
                                type = StorageUtils.getTypeConstant(mimeType),
                                createTime = documentFile.lastModified()
                                    .convertTimeForChat().toString(),
                                uri = documentFile.uri
                            )
                            videoList.add(media)
                        }
                        _uiState.value = HomeUiState.Success(imageList = imageList)
                    }
                }
            }
        }
    }

    @SuppressLint("WrongConstant")
    fun onPermissionGranted(data: Intent, context: Context) {
        val uri = data.data
        if (uri!!.path!!.endsWith(".Statuses")) {
            val takeFlags = data.flags and Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(
                uri,
                takeFlags
            )
            SharedPrefs.documentUri = uri.toString()
            SharedPrefs.isPermissionGranted = true
            getStatuses(context)
        }
    }

}