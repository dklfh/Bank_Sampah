package com.example.banksampah

//import android.os.Bundle
//import android.view.View
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Spinner
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//class add_item_subkategori : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.add_item_subkategori)
//
//        // Mendapatkan referensi Spinner dari layout
//        val spinner = findViewById<Spinner>(R.id.UserNameSubKat)
//
//        // Membuat adapter untuk Spinner
//        val adapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.satuan_array,
//            android.R.layout.simple_spinner_item
//        )
//
//        // Menentukan tata letak yang akan digunakan saat daftar tampil
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        // Menetapkan adapter ke Spinner
//        spinner.adapter = adapter
//
//        // Menetapkan listener untuk menangani perubahan item yang dipilih
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                // Item yang dipilih
//                val selectedItem = parent.getItemAtPosition(position).toString()
//                // Lakukan sesuatu dengan item yang dipilih, misalnya:
//                Toast.makeText(
//                    this@add_item_subkategori,
//                    "Selected: $selectedItem",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Do nothing
//            }
//        }
//    }
//}