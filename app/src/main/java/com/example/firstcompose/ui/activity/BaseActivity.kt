package com.example.firstcompose.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.activity.ComponentActivity

open class BaseActivity : ComponentActivity(){

    fun isNetworkConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
    fun showToast(msg: String) {
       Toast.makeText(this@BaseActivity,msg,Toast.LENGTH_SHORT).show()
    }
}