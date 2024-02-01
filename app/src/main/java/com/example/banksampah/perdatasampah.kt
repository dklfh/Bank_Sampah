package com.example.banksampah

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

//interface OverlayListener {
//    fun onHideOverlay()
//}
class PerdataSampah : Activity() {

    private var overlayListener: OverlayListener? = null
    private val periode = arrayOf("1 Bulan", "6 Bulan", "1 Tahun")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perdatasampah)

        // Inisialisasi overlayListener dengan objek cobanavbar
        val overlayManager = cobanavbar()
        overlayListener = overlayManager

        val batalButton: Button = findViewById(R.id.batal)
        batalButton.setOnClickListener {
            overlayListener?.onHideOverlay()
            finish()
        }

        // Inisialisasi Spinner
        val spinner = findViewById<Spinner>(R.id.periode)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, periode)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Not implemented
            }
        }
    }

//    private fun tutupOverlay() {
//        // Tambahkan kode untuk menutup overlay di sini
//        finish() // Ini akan menutup activity dan menghapus overlay dari tumpukan tampilan
//    }
}
