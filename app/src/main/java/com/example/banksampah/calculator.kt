package com.example.banksampah

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class calculator : Fragment() {
    private var currentValue = 0
    private lateinit var number: TextView
    private lateinit var btnPlus : Button
    private lateinit var btnMinus : Button
    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button
    private lateinit var btnBersihkan: Button
    private lateinit var namaBank : EditText
    private lateinit var namaPetugas : EditText
    private lateinit var namaNasabah : EditText
    private lateinit var username : EditText
    private lateinit var rekening : EditText
    private lateinit var noTelp : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        tvDatePicker = view.findViewById(R.id.tvDate)
        btnDatePicker = view.findViewById(R.id.btnDatePicker)
        number = view.findViewById(R.id.number)
        namaBank = view.findViewById(R.id.namaBank)
        namaPetugas = view.findViewById(R.id.namaPetugas)
        namaNasabah = view.findViewById(R.id.namaNasabah)
        username = view.findViewById(R.id.username)
        rekening = view.findViewById(R.id.rekening)
        noTelp = view.findViewById(R.id.noTelp)
        btnPlus = view.findViewById(R.id.btnPlus)
        btnMinus = view.findViewById(R.id.btnMinus)
        btnBersihkan = view.findViewById(R.id.buttonBersihkan)
        updateTextView()

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        btnDatePicker.setOnClickListener {
            DatePickerDialog(requireActivity(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnPlus.setOnClickListener {
            currentValue++
            updateTextView()
        }

        btnMinus.setOnClickListener {
            if (currentValue > 0) {
                currentValue--
                updateTextView()
            }
        }

        btnBersihkan.setOnClickListener {
            currentValue = 0
            updateTextView()
            tvDatePicker.text = null
            namaBank.text = null
            namaPetugas.text = null
            namaNasabah.text = null
            username.text = null
            rekening.text = null
            noTelp.text = null
        }
        return view
    }

    private fun updateTextView() {
        number.text = currentValue.toString()
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
    }
}