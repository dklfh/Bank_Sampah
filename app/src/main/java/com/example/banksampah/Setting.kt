package com.example.banksampah

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import android.widget.TextView


class Setting : Fragment() {
    private lateinit var aboutusbtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        aboutusbtn = view.findViewById(R.id.aboutus)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutusbtn.setOnClickListener {
            val intent = Intent(activity, aboutus::class.java)
            startActivity(intent)
        }

        // Check if user is already logged in
        if (FirebaseAuth.getInstance().currentUser != null) {
            // User is already logged in, redirect to calculator activity
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }
}