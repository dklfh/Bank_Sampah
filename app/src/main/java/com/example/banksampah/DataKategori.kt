package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView

class DataKategori : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_kategori)

        findViewById<AutoCompleteTextView>(R.id.dropdown_list)
        setAccessibilityDelegate()

        val dropdownList = findViewById<AutoCompleteTextView>(R.id.dropdown_list)
        dropdownList.contentDescription = "Deskripsi dropdown list"
    }
}

private fun setAccessibilityDelegate() {
    TODO("Not yet implemented")
}
