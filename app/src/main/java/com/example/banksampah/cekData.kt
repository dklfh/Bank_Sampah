package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.app.Activity
import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
import com.example.banksampah.calculator.DataTransaksi
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class cekData : AppCompatActivity() {
    private lateinit var btnBatal: Button
    private lateinit var namaBank: TextView
    private lateinit var namaPetugas: TextView
    private lateinit var namaNasabah: TextView
    private lateinit var username: TextView
    private lateinit var noTelp: TextView
    private lateinit var tanggal: TextView
    private lateinit var pembayaran: TextView
    private lateinit var judulpembayaran: TextView
    private lateinit var kategori: TextView
    private lateinit var subkategori: TextView
    private lateinit var jumlah: TextView
    private lateinit var harga: TextView
    private lateinit var subtotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_data)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("transactions")
        val submitButton: Button = findViewById(R.id.submitbtn)
        val dataTransaksi = intent.getSerializableExtra("dataTransaksi") as DataTransaksi
        val containerLayout: LinearLayout = findViewById(R.id.containerDataSampah)
        val metodePembayaran = dataTransaksi.pembayaran
        namaBank = findViewById(R.id.namaBank)
        namaPetugas = findViewById(R.id.namaPetugas)
        namaNasabah = findViewById(R.id.namaNasabah)
        tanggal = findViewById(R.id.tanggal)
        username = findViewById(R.id.username)
        noTelp = findViewById(R.id.noTelp)
        pembayaran = findViewById(R.id.pembayaran)
        judulpembayaran = findViewById(R.id.judulpembayaran)
        kategori = findViewById(R.id.kategori)
        subkategori = findViewById(R.id.subkategori)
        jumlah = findViewById(R.id.jumlah)
        harga = findViewById(R.id.harga)
        subtotal = findViewById(R.id.subtotal)
        judulpembayaran.text = if (metodePembayaran in listOf("BRI", "BNI", "BCA")) {
            "No Rekening"
        } else {
            "No Telepon"
        }
        namaBank.text = dataTransaksi.namaBank
        namaPetugas.text = dataTransaksi.namaPetugas
        namaNasabah.text = dataTransaksi.namaNasabah
        tanggal.text = dataTransaksi.tanggal
        username.text = dataTransaksi.username
        noTelp.text = dataTransaksi.rekening
        pembayaran.text = dataTransaksi.pembayaran
        kategori.text = dataTransaksi.kategori
        subkategori.text = dataTransaksi.subkategori
        jumlah.text = "${dataTransaksi.jumlah} kg"
        harga.text = "Rp. " + (dataTransaksi.hargaSubKategori?.toInt() ?: 0) + " /kg"
        subtotal.text = "Rp. ${dataTransaksi.subtotal?.toInt() ?: 0}"

        for (dataSampah in dataTransaksi.listDataSampah) {
            val dataSampahLayout = layoutInflater.inflate(R.layout.notasampah, null)
            val kategoriTextView: TextView = dataSampahLayout.findViewById(R.id.kategori)
            val subkategoriTextView: TextView = dataSampahLayout.findViewById(R.id.subkategori)
            val jumlahTextView: TextView = dataSampahLayout.findViewById(R.id.jumlah)
            val hargaTextView: TextView = dataSampahLayout.findViewById(R.id.hargasampah)
            val subtotalTextView: TextView = dataSampahLayout.findViewById(R.id.subtotalsampah)
            kategoriTextView.text = dataSampah.kategori
            subkategoriTextView.text = dataSampah.subkategori
            jumlahTextView.text = dataSampah.jumlah.toString()
            hargaTextView.text = dataSampah.harga.toString()
            subtotalTextView.text = dataSampah.subtotal.toString()
            containerLayout.addView(dataSampahLayout)
        }

        submitButton.setOnClickListener {
            val currentDate = Date()
            val dateFormatForFirebase = SimpleDateFormat("yyyy-MM-dd, HH.mm.ss", Locale.getDefault())
            val timestamp = dateFormatForFirebase.format(currentDate).replace(".", ",")

            val intent = Intent(this, nota::class.java)
            val formattedDate = dateFormatForFirebase.format(currentDate)
            dataTransaksi.tanggal = formattedDate
            intent.putExtra("dataTransaksi", dataTransaksi)

            myRef.push().setValue(dataTransaksi)
                .addOnSuccessListener {
                    // Handle success, if needed
                    myRef.child(timestamp).setValue(dataTransaksi)
                }
                .addOnFailureListener {
                    // Handle failure, if needed
                }

            startActivity(intent)
        }



        btnBatal = findViewById(R.id.cancelbtn)

        btnBatal.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
