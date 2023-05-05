package com.nirav.statussaver.data.model

import android.net.Uri
import java.io.Serializable

data class FileMedia(
    val path: String? = null,
    val type: Int? = null,
    var duration: String? = null,
    val fileName: String? = null,
    val createTime: String? = null,
    val uriString: String? = null,
    val createTimeStamp: Long? = null,
    val fileSize: String? = null,
    val mimeType: String? = null,
    val uri: Uri? = null
) : Serializable
