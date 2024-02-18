package com.example.banksampah

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class notasampah : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notasampah) // Pastikan R.layout.notasampah sesuai dengan nama layout XML Anda

        // Ambil referensi ke elemen-elemen di layout notasampah
        val kategoriTextView: TextView = findViewById(R.id.kategori)
        val subkategoriTextView: TextView = findViewById(R.id.subkategori)
        val jumlahTextView: TextView = findViewById(R.id.jumlah)
        val hargaTextView: TextView = findViewById(R.id.hargasampah)
        val subtotalTextView: TextView = findViewById(R.id.subtotalsampah)

        val dataSampah: calculator.DataTransaksi.DataSampah = intent.getSerializableExtra("dataSampah") as calculator.DataTransaksi.DataSampah

        // Set nilai elemen-elemen di layout notasampah dengan data dari intent
        kategoriTextView.text = dataSampah.kategori
        subkategoriTextView.text = dataSampah.subkategori
        jumlahTextView.text = "${dataSampah.jumlah} kg"
        hargaTextView.text = "Rp. ${dataSampah.harga} /kg"
        subtotalTextView.text = "Rp. ${dataSampah.subtotal}"
    }
}
