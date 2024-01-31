package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.app.Activity
import android.content.Intent
import android.widget.TextView
import com.example.banksampah.calculator.DataTransaksi


//import android.widget.TextView

class cekData : AppCompatActivity() {
    private lateinit var btnBatal : Button
    private lateinit var namaBank : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_data)
        val dataTransaksi = intent.getSerializableExtra("dataTransaksi") as DataTransaksi
        namaBank = findViewById(R.id.namaBank)
        namaBank.text = dataTransaksi.namaBank

        btnBatal = findViewById(R.id.cancelbtn)

        btnBatal.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}