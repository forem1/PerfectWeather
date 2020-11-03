package com.example.perfectweather.ui.celectcity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.perfectweather.R
import kotlinx.android.synthetic.main.fragment_selectcity.*


class CelectCityFragment : Fragment(), View.OnClickListener {

    private lateinit var celectCityViewModel: CelectCityViewModel
    var button: Button? = null
    var button1: Button? = null
    //val APP_PREFERENCES = "WeatherApp"
    //val APP_PREFERENCES_CurrentCityPosition = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        celectCityViewModel = ViewModelProviders.of(this).get(CelectCityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_selectcity, container, false)

        /*var mSettings = activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        var tempPosition = 0
        tempPosition = mSettings?.getInt(APP_PREFERENCES_CurrentCityPosition, 0)!!
        selectCity_spinner.setSelection(tempPosition.toInt())*/

        button1 = root.findViewById(R.id.button1) as Button
        button1!!.setOnClickListener(this)
        button = root.findViewById(R.id.selectCity_button) as Button
        button!!.setOnClickListener(this)


        return root
    }

    override fun onClick(root: View?) {
        if(translateIdToIndex(root!!.id) == 1) {

            Toast.makeText(
                activity, "Сохранено",
                Toast.LENGTH_SHORT).show()
        }
        if(translateIdToIndex(root!!.id) == 2) {
            val settings = requireContext().getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
            settings.edit().clear().commit()
            Toast.makeText(
                activity, "удалено",
                Toast.LENGTH_SHORT).show()
        }
    }

    fun translateIdToIndex(id: Int): Int {
        var index = -1
        when (id) {
            R.id.selectCity_button -> index = 1
            R.id.button1 -> index = 2
            //R.id.button3 -> index = 3
        }
        return index
    }
}
