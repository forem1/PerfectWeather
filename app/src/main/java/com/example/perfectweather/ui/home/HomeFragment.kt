package com.example.perfectweather.ui.home

import android.R.attr.name
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.perfectweather.BuildConfig
import com.example.perfectweather.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


@Parcelize
data class Weather (
    var weather: String,
    var weatherDescription: String,
    var temperature: String,
    var humidity: String,
    var pressure: String,
    var region: String
): Parcelable

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var okHttpClient: OkHttpClient = OkHttpClient()
    val APP_PREFERENCES = "WeatherApp"
    val APP_PREFERENCES_CurrentCity = ""
    lateinit var weatherParams: Weather

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val mSettings = getActivity()?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val URL = "http://api.openweathermap.org/data/2.5/weather?q=" + mSettings?.getString(
            APP_PREFERENCES_CurrentCity,
            ""
        ) + "&units=metric&lang=ru&APPID="+BuildConfig.API_KEY;
        GetAPIData(URL, root)

        return root
    }

    fun GetAPIData(URL: String, root: View) {
        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()

                if (JSONObject(json).getString("name") != "") {
                    var weather =
                        JSONObject(JSONObject(json).getJSONArray("weather")[0].toString()).getString(
                            "main"
                        )
                    var weatherDescription =
                        JSONObject(JSONObject(json).getJSONArray("weather")[0].toString()).getString(
                            "description"
                        )
                    var temperature =
                        JSONObject(JSONObject(json).getString("main")).getString("temp").split(".")[0]
                    var humidity = JSONObject(JSONObject(json).getString("main")).getString("humidity")
                    var pressure = JSONObject(JSONObject(json).getString("main")).getString("pressure")
                    var region = JSONObject(json).getString("name")
                    weatherParams = Weather(weather, weatherDescription, temperature, humidity, pressure, region)

                    getActivity()?.runOnUiThread {
                        textView3.text = region
                        textView4.text = temperature
                    }
                } else Toast.makeText(
                    activity,
                    getString(R.string.connection_error_parse),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }
}
