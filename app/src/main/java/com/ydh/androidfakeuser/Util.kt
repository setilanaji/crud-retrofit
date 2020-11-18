package com.ydh.androidfakeuser

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

object Util {

    private var progressBar: ProgressBar? = null

    fun Context.isInternetAvailable(): Boolean {
        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
//
//    fun Context.showErrorToast(message: String?) {
//
//        try {
//            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
//
//            // set message color
//            val textView = toast.view?.findViewById(android.R.id.message) as? TextView
//            textView?.setTextColor(Color.WHITE)
//            textView?.gravity = Gravity.CENTER
//
//            // set background color
//            toast.view?.setBackgroundColor(Color.RED)
//
//            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
//
//            toast.show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    // show progressbar
    fun Context.showProgressBar() {
        try {
             progressBar = (this as? Activity)?.findViewById(R.id.progress_circular)

            progressBar.let { it?.visibility = View.VISIBLE }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
//    // hide progressbar
    fun hideProgressBar() {
        try {
            progressBar?.let {
                it.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}