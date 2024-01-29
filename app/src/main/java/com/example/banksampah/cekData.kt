package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.app.Activity
import android.content.Intent

//import android.widget.TextView

class cekData : AppCompatActivity() {
//    private lateinit var textView: TextView
    private lateinit var btnBatal : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_data)
        btnBatal = findViewById(R.id.cancelbtn)
//
//        textView = findViewById(R.id.namaBank)
//        textView.text = "Nama Bank Sampah :"
//        textView = findViewById(R.id.namaPetugas)
//        textView = findViewById(R.id.namaNasabah)
//        textView = findViewById(R.id.tanggal)
//        textView = findViewById(R.id.username)
//        textView = findViewById(R.id.tanggal)
//        textView = findViewById(R.id.transkasi)
//        textView = findViewById(R.id.noTelpon)
//        textView = findViewById(R.id.kategori)
//        textView = findViewById(R.id.sub_kategori)
//        textView = findViewById(R.id.jumlah)
//        textView = findViewById(R.id.harga)
//        textView = findViewById(R.id.total)
//        textView = findViewById(R.id.totjum)
//        textView = findViewById(R.id.tothar)

        btnBatal.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}