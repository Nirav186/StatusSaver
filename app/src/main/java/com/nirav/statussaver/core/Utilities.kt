package com.nirav.statussaver.core

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.WindowInsetsControllerCompat
import java.io.File
import java.net.URLConnection


fun Context.openApp(packageName: String?, uri: String?) {
    var intent: Intent?
    val manager = packageManager
    try {
        intent = (packageName?.let { manager.getLaunchIntentForPackage(it)})
        if (intent == null) throw PackageManager.NameNotFoundException()
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        startActivity(intent)
    } catch (e: PackageManager.NameNotFoundException) {
        Toast.makeText(
            this,
            "App is not installed",
            Toast.LENGTH_SHORT
        ).show()
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.pasteFromClipboard(): String? {
    var data = ""
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        ?: return data
    val clip = clipboard.primaryClip ?: return data
    val item = clip.getItemAt(0) ?: return data
    val textToPaste = item.text
    return if (textToPaste == null) {
        data
    } else {
        data = textToPaste.toString()
        data
    }
}

fun ordinalIndexOf(str: String, substr: String?, n: Int): Int {
    var n = n
    var pos = -1
    do {
        pos = str.indexOf(substr!!, pos + 1)
    } while (n-- > 0 && pos != -1)
    return pos
}


fun Context.appInstalledOrNot(uri: String): Boolean {
    val pm = packageManager
    var appInstalled = false
    appInstalled = try {
        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
    return appInstalled
}

fun getFormatType(path: String): String {
    val fileNameMap = URLConnection.getFileNameMap()
    return fileNameMap.getContentTypeFor("file://$path")
}

fun Context.getUri( file: File?): Uri {
    val uri: Uri = if (Build.VERSION.SDK_INT > 21) {
        FileProvider.getUriForFile(this, "$packageName.provider", file!!)
    } else {
        Uri.fromFile(file)
    }
    return uri
}


fun isAppInstalled(context: Context, packageName: String?): Boolean {
    return try {
        context.packageManager.getApplicationInfo(packageName!!, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Activity.changeStatusBarColor(color: Int, isLight: Boolean = false) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color

    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isLight
}
fun Activity.backGroundColor(resId : Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
//    window.setBackgroundDrawableResource(resId)
    val rightArrow = ContextCompat.getDrawable(this,resId)
    rightArrow?.alpha = 229
    window.setBackgroundDrawable(rightArrow)
}