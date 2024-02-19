package com.example.banksampah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.content.Intent

class Setting : Fragment() {
    private lateinit var aboutusbtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        aboutusbtn = view.findViewById(R.id.aboutus)
        aboutusbtn.setOnClickListener {
            val intent = Intent(activity, aboutus::class.java)
            startActivity(intent)
        }
        return view
    }
}
