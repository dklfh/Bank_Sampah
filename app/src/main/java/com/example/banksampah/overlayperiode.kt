package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
class overlayperiode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlayperiode)

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