package com.example.banksampah

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class perdatasampah : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perdatasampah)

        val batalButton: Button = findViewById(R.id.batal)
        batalButton.setOnClickListener {
            // Panggil metode untuk menutup overlay di sini
            tutupOverlay()
        }
    }

    private fun tutupOverlay() {
        // Tambahkan kode untuk menutup overlay di sini
        finish() // Ini akan menutup activity dan menghapus overlay dari tumpukan tampilan
    }
}
