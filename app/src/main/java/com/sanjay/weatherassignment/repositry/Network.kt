package com.sanjay.weatherassignment.repositry

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.fragment.app.FragmentActivity
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class Network(var activity: FragmentActivity) {
    fun verifyAvailableNetwork(): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun httpRequest(url: String, httpResponse: HttpResponse) {
        Log.d("URL_REQUEST", url)
        if (verifyAvailableNetwork()) {
            val client = OkHttpClient()
            val request = okhttp3.Request.Builder().url(url).build()
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    Log.d("HTTP_REQUEST", response?.body().toString())
                    httpResponse.httpResponseSuccess(response?.body()?.string()!!)
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    e?.printStackTrace()
                }
            })
        } else {
            Log.e("TAG", "httpRequest: No network available")
        }
    }
}