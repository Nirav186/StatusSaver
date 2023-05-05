package com.nirav.statussaver.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPrefs {

    private lateinit var context: Context

    private fun getContext() = if (::context.isInitialized) context
    else throw RuntimeException("Please Initialize SharedPrefs")

    private var initialized = false

    private val pref by lazy {
        getInstance().getContext()
            .getSharedPreferences("Karyakar_Share_Prefs", Context.MODE_PRIVATE)
    }

    private fun edit(operation: SharedPreferences.Editor.() -> Unit) {
        val editor = getInstance().pref.edit()
        operation(editor)
        editor.apply()
    }

    companion object {
        fun init(context: Context) {
            if (getInstance().initialized.not()) {
                getInstance().context = context
                getInstance().initialized = true
            } else println("Already initialized")
        }

        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPrefs? = null

        private fun getInstance(): SharedPrefs {
            return instance ?: synchronized(this) { SharedPrefs().also { instance = it } }
        }

//        fun clear() {
//            isPermissionGranted = null
//        }

        var isPermissionGranted: Boolean
            get() = getInstance().pref.getBoolean("isPermissionGranted", false)
            set(value) = getInstance().edit { putBoolean("isPermissionGranted", value) }

        var documentUri: String?
            get() = getInstance().pref.getString("documentUri", null)
            set(value) = getInstance().edit { putString("documentUri", value) }

    }
}