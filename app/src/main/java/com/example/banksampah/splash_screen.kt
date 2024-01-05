package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.content.Intent

class splash_screen : AppCompatActivity() {

    private val splashTimeOut: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, DataKategori::class.java)
            startActivity(intent)

            finish()
        }, splashTimeOut)
    }
}