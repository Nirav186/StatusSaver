package com.nirav.statussaver.core

import androidx.documentfile.provider.DocumentFile

object Constants {
    interface BottomNavigationItemRoute {
        companion object {
            const val HOME = "HOME"
            const val DOWNLOADS = "DOWNLOADS"
            const val SETTINGS = "SETTINGS"
        }
    }

    // file type constants
    const val TYPE_IMAGE = 1362
    const val TYPE_VIDEO = 2137
    const val TYPE_AUDIO = 6854
    const val TYPE_DOC = 5416

    var docFile: DocumentFile? = null
}