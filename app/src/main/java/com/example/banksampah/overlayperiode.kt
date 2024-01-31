package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

interface OverlayListener {
    fun onHideOverlay()
}

class OverlayPeriode : AppCompatActivity() {
    private var overlayListener: OverlayListener? = null

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
    }
}
