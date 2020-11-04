package com.example.perfectweather.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.perfectweather.R
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var okHttpClient: OkHttpClient = OkHttpClient()
    val APP_PREFERENCES = "WeatherApp"
    val APP_PREFERENCES_CurrentCity = ""

    var weather = ""
    var weatherDescription = ""
    var temperature = ""
    var humidity = ""
    var pressure = ""
    var region = ""

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
        ) + "&units=metric&lang=ru&APPID=5488ff05b7eba5bf321c854a7843578f";
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
                    weather =
                        JSONObject(JSONObject(json).getJSONArray("weather")[0].toString()).getString(
                            "main"
                        )
                    weatherDescription =
                        JSONObject(JSONObject(json).getJSONArray("weather")[0].toString()).getString(
                            "description"
                        )
                    temperature =
                        JSONObject(JSONObject(json).getString("main")).getString("temp").split(".")[0]
                    humidity = JSONObject(JSONObject(json).getString("main")).getString("humidity")
                    pressure = JSONObject(JSONObject(json).getString("main")).getString("pressure")
                    region = JSONObject(json).getString("name")

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
