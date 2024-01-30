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
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import java.io.Serializable
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
    private lateinit var btnKirim : Button
    private lateinit var namaBank : EditText
    private lateinit var namaPetugas : EditText
    private lateinit var namaNasabah : EditText
    private lateinit var username : EditText
    private lateinit var rekening : EditText
    private lateinit var noTelp : EditText
    private val REQUEST_CODE_SECOND_ACTIVITY = 1

//    kirim data
    data class DataTransaksi(
        val namaBank: String,
        val namaPetugas: String,
        val namaNasabah: String,
        val tanggal: String,
        val username: String,
        val rekening: String,
        val noTelp: String
    ) : Serializable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Variabel namaKategori
    private val nKategori = arrayOf("Plastik RIGD/Berbentuk", "Gelasan", "Plastik Fleksibel atau Lembaran"
            ,"Plastik Kerasan", "Kertas" , "Logam" , "Kaca" , "PET" ,"Lainnya")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_datakategorii, container, false)
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        val btnDatePicker: LinearLayout = view.findViewById(R.id.btnDatePicker)
        tvDatePicker = view.findViewById(R.id.tvDate)
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
        btnKirim = view.findViewById(R.id.btnKirim)


        updateTextView()

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { View, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        val dataTransaksi = DataTransaksi(
            namaBank = namaBank.text.toString().trim(),
            namaPetugas = namaPetugas.text.toString().trim(),
            namaNasabah = namaNasabah.text.toString().trim(),
            tanggal = tvDatePicker.text.toString().trim(),
            username = username.text.toString().trim(),
            rekening = rekening.text.toString().trim(),
            noTelp = noTelp.text.toString().trim()
        )

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

        btnKirim.setOnClickListener {
            val inputText1 = namaBank.text.toString().trim()
            val inputText2 = namaPetugas.text.toString().trim()
            val inputText3 = namaNasabah.text.toString().trim()
            val inputText4 = username.text.toString().trim()
            val inputText5 = rekening.text.toString().trim()
            val inputText6 = noTelp.text.toString().trim()
            val inputDate = tvDatePicker.text.toString().trim()
            var isAllFieldsFilled = true
            if (inputText1.isEmpty()) {
                namaBank.error = "Kolom harus diisi!"
                isAllFieldsFilled = false
            }
            if (inputText2.isEmpty()) {
                namaPetugas.error = "Kolom harus diisi!"
                isAllFieldsFilled = false
            }
            if (inputText3.isEmpty()) {
                namaNasabah.error = "Kolom harus diisi!"
                isAllFieldsFilled = false
            }
            if (inputText4.isEmpty()) {
                username.error = "Kolom harus diisi!"
                isAllFieldsFilled = false
            }
            if (inputText5.isEmpty()) {
                rekening.error = "Kolom harus diisi!"
                isAllFieldsFilled = false
            }
            if (inputText6.isEmpty()) {
                noTelp.error = "Kolom harus diisi!"
                isAllFieldsFilled = false
            }
            if (inputDate.isEmpty()) {
                tvDatePicker.error = "Kolom harus diisi!"
                isAllFieldsFilled = false
            }
            if (isAllFieldsFilled) {
                val intent = Intent(activity, cekData::class.java)
                intent.putExtra("dataTransaksi", dataTransaksi)
                startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY)
            }
        }

        // Dropdown menu
        // Inisialisasi Spinner
        val spinner = view.findViewById<Spinner>(R.id.spinner3)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, nKategori)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(requireContext(), "Selected Category is = ${nKategori[position]}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
            }
        }
    }


    private fun updateTextView() {
        number.text = currentValue.toString()
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
        tvDatePicker.error = null
    }
}