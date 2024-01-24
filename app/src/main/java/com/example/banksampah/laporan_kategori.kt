package com.example.banksampah

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class laporan_kategori : AppCompatActivity() {

    private lateinit var tglAwalEditText: EditText
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_kategori)

        tglAwalEditText = findViewById(R.id.tgl_awal)

        // Set initial date to the current date
        updateLabel()

        // Set click listener to show DatePickerDialog
        tglAwalEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update the calendar with the selected date
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update the EditText with the selected date
                updateLabel()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePickerDialog
        datePicker.show()
    }

    private fun updateLabel() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        tglAwalEditText.setText(dateFormat.format(calendar.time))
    }
}
