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
    private lateinit var namaPetugas : TextView
    private lateinit var namaNasabah : TextView
    private lateinit var username : TextView
    private lateinit var noTelp : TextView
    private lateinit var tanggal : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_data)
        val dataTransaksi = intent.getSerializableExtra("dataTransaksi") as DataTransaksi
        namaBank = findViewById(R.id.namaBank)
        namaPetugas = findViewById(R.id.namaPetugas)
        namaNasabah = findViewById(R.id.namaNasabah)
        tanggal = findViewById(R.id.tanggal)
        username = findViewById(R.id.username)
        noTelp = findViewById(R.id.noTelp)
        namaBank.text = dataTransaksi.namaBank
        namaPetugas.text = dataTransaksi.namaPetugas
        namaNasabah.text = dataTransaksi.namaNasabah
        tanggal.text = dataTransaksi.tanggal
        username.text = dataTransaksi.username
        noTelp.text = dataTransaksi.noTelp

        btnBatal = findViewById(R.id.cancelbtn)

        btnBatal.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}