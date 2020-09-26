package com.sanjay.weatherassignment.OpenWeatherMap

import androidx.fragment.app.FragmentActivity
import com.sanjay.weatherassignment.model.Location
import com.sanjay.weatherassignment.model.LocationViewModel
import com.sanjay.weatherassignment.repositry.HttpResponse
import com.sanjay.weatherassignment.repositry.Network
import org.json.JSONObject

class OpenWeatherMap(var activity: FragmentActivity) {
    private val URL_BASE = "https://api.openweathermap.org/"
    private val VERSION = "2.5/"
    private val API_ID = "&appid=fae7190d7e6433ec3a45285ffcf55c86"
    fun getWeatherByLocation(
        lat: Double,
        lon: Double,
        locationViewModel: LocationViewModel
    ) {
        val network = Network(activity)
        val section = "data/"
        val method = "weather?"
        val args = "lat=$lat&lon=$lon"
        val url = "$URL_BASE$section$VERSION$method$args$API_ID"
        network.httpRequest(url, object : HttpResponse {
            override fun httpResponseSuccess(response: String) {
                var jsonResponse = JSONObject(response)
                var weatherArray = jsonResponse.getJSONArray("weather")
                var mainObj = jsonResponse.getJSONObject("main")
                var windObj = jsonResponse.getJSONObject("wind")
                var coordObj = jsonResponse.getJSONObject("coord")
                var name = jsonResponse.getString("name")
                var weatherObj = weatherArray.getJSONObject(0)
                var description = weatherObj.getString("description")
                var icon = weatherObj.getString("icon")
                var temperature = mainObj.getString("temp") + " F"
                var speed = windObj.getString("speed") + " Km/h"

                locationViewModel.insert(
                    Location(
                        latitude = coordObj.getDouble("lat"),
                        longitude = coordObj.getDouble("lon"),
                        name = name,
                        description = description,
                        temperature = temperature,
                        speed = speed,
                        icon = makeIconURL(icon)
                    )
                )
            }
        })
    }

    private fun makeIconURL(icon: String): String {
        val secion = "img/w/"
        val url = "$URL_BASE$secion$icon.png"
        return url
    }
}