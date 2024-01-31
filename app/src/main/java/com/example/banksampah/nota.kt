package com.example.banksampah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class nota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota)

        setContentView(R.layout.activity_nota)
        val cancelButton: Button = findViewById(R.id.cancelbtn)
        cancelButton.setOnClickListener {
            val intent = Intent(this, cobanavbar::class.java)
            startActivity(intent)
        }
    }
}