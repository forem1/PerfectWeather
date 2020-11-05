package com.example.perfectweather

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*


class Welcome : AppCompatActivity() {

    val APP_PREFERENCES = "WeatherApp"
    val APP_PREFERENCES_CurrentCity = ""
    //val APP_PREFERENCES_CurrentCityPosition = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_welcome)
        getSupportActionBar()?.hide()

        var mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        if(mSettings.getString(APP_PREFERENCES_CurrentCity, "") != "") {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        Welcome_enter.setOnClickListener {
            val editor = mSettings.edit()
            editor.putString(APP_PREFERENCES_CurrentCity, Welcome_spinner.getSelectedItem().toString())
            //editor.putString(APP_PREFERENCES_CurrentCityPosition, Welcome_spinner.getSelectedItemPosition().toString())
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}


