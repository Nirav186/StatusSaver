package com.nirav.statussaver

import android.app.Application
import android.content.Context
import com.nirav.statussaver.core.SharedPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StatusSaver : Application() {

//    companion object {
//        lateinit var appContext: Context
//    }

    override fun onCreate() {
        super.onCreate()
//        appContext = this
        SharedPrefs.init(this)
    }

}