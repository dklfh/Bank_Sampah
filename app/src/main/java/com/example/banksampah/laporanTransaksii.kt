package com.example.banksampah

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.banksampah.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class laporanTransaksii : Fragment() {

    private lateinit var tglAwalTextView: TextView
    private lateinit var tglAkhirTextView: TextView
    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_laporan_transaksii, container, false)

        tglAwalTextView = rootView.findViewById(R.id.tgl_awal)
        tglAkhirTextView = rootView.findViewById(R.id.tgl_akhir)

        // Set click listener to show DatePickerDialog
        tglAwalTextView.setOnClickListener {
            showDatePickerDialog(tglAwalTextView)
        }

        tglAkhirTextView.setOnClickListener {
            showDatePickerDialog(tglAkhirTextView)
        }

        return rootView
    }

    private fun showDatePickerDialog(textView: TextView) {
        val datePicker = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update the calendar with the selected date
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update the TextView with the selected date
                updateLabel(textView)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePickerDialog
        datePicker.show()
    }

    private fun updateLabel(textView: TextView) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        textView.text = dateFormat.format(calendar.time)
    }
}