package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

interface OverlayListener {
    fun onHideOverlay()
}

class OverlayPeriode : AppCompatActivity() {
    private var overlayListener: OverlayListener? = null
    private val periode = arrayOf("1 Bulan", "6 Bulan", "1 Tahun")

    fun setOverlayListener(listener: OverlayListener) {
        overlayListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlayperiode)

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
}

