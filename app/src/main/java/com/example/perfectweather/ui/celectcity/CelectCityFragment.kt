package com.example.perfectweather.ui.celectcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.perfectweather.R

class CelectCityFragment : Fragment() {

    private lateinit var celectCityViewModel: CelectCityViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        celectCityViewModel =
                ViewModelProviders.of(this).get(CelectCityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_selectcity, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        celectCityViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
