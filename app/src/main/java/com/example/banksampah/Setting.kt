package com.example.banksampah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class Setting : Fragment() {
    private lateinit var aboutus: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        aboutus = view.findViewById(R.id.aboutus)
        aboutus.setOnClickListener {
            findNavController().navigate(R.id.action_setting_to_about_us)
        }
        return view
    }
}
