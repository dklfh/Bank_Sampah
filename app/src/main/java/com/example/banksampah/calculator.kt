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
import android.widget.RadioGroup
import android.widget.Spinner
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.math.BigDecimal
import android.widget.RadioButton


class calculator : Fragment() {
    private var currentValue = BigDecimal("1.0")
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
    private var pembayaran: String = ""
    private lateinit var judulRekening : TextView
    private var hargaSubKategori: Double? = null
    private var selectedKategori: String = ""
    private var selectedSubKategori: String = ""
    private val currentValueList: MutableList<BigDecimal> = mutableListOf(BigDecimal("1.0"))
    private lateinit var spinner2 : Spinner
    private val REQUEST_CODE_SECOND_ACTIVITY = 1
    private var dataTransaksi = DataTransaksi("", "", "", "", "", "", "", "", "", 0.0, 0.0,0.0)
    private var isSwitchActive = false
    private lateinit var plus : Button
    private lateinit var minus : Button
    private var dataSampahLayout: LinearLayout? = null

//    kirim data
    data class DataTransaksi(
        val namaBank: String,
        val namaPetugas: String,
        val namaNasabah: String,
        val tanggal: String,
        val username: String,
        val rekening: String,
        val pembayaran: String,
        val kategori: String,
        val subkategori: String,
        val jumlah: Double?,
        val hargaSubKategori : Double?,
        val subtotal : Double?,
        val layoutDataList: List<LayoutData> = emptyList(),
        val listDataSampah: List<DataSampah> = emptyList()
    ) : Serializable {

    data class DataSampah(
        val kategori: String,
        val subkategori: String,
        val jumlah: Double?,
        val harga: Double?,
        val subtotal: Double?
    ) : Serializable
    data class LayoutData(
        val selectedKategori: String,
        val selectedSubKategori: String,
        val jumlah: Double?,
        val hargaSubKategori: Double?,
        val subtotal: Double?
    ) : Serializable
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val hargaSubKategoriMap = mapOf(
        "Putihan (HDPE & Jenis Lainnya)" to 1200.0, "Jerigen Bening Bersih / Jerigen minyak" to 3250.0, "Jerigen warna / bening tapi mangkak" to 1860.0, "Tutup PET (HDPE)" to  2650.0, "Tutup Galon (LDPE)" to 2650.0, "Tutup Campur" to 1750.0, "PP Emberan Campur" to 1000.0, "PP Emberan Warna" to 400.0, "PP Emberan Hitam" to 400.0, "PC Galon UTUH / BIJI" to 3250.0, "PC Galon PECAH / KG" to 150.0,
        "Aqua Gelas Kotor" to 1000.0, "Aqua Gelas Bersih" to 2700.0, "Mountea/Sablon" to 500.0,
        "Plastik Bening PE & PP" to 500.0, "HD Kresek" to 300.0, "Kresek Campur" to 150.0, "LLDPE Pouch" to 150.0, "PP Sablon Lembaran" to 200.0, "Multi Layer" to 50.0,
        "Kerasan / PS / ABS" to 700.0, "PVC" to 600.0,
        "Buku Tulis/Buku Pelajaran" to 900.0, "HVS" to 500.0, "Kertas Buram/LKS/warna" to 500.0, "Majalah" to 350.0, "Koran" to 1750.0, "Duplek" to 700.0, "Kardus" to 700.0, "Kertas Campur" to 650.0,
        "Besi Kaleng (Omplong)" to 800.0, "Besi B (Tipis)" to 1000.0, "Besi A (Tebal & Padat)" to 2000.0, "Seng / paku" to 150.0, "Aluminium Kaleng/tipis" to 7500.0, "Aluminium Tebal" to 9500.0, "Tembaga" to 28000.0, "Logam Campur" to 600.0,
        "Botol Bir Bintang Besar/biji" to 600.0, "Botol Bir Bintang Kecil/biji" to 150.0, "Botol Kaca Campur" to 100.0,
        "Rongsok Campur" to 1000.0, "PET KW 2" to 1500.0, "PET Bersih Bening" to 600.0, "PET Bersih Biru Muda" to 3150.0, "PET Bersih Warna" to 2650.0, "PET Bersih Campur" to 2000.0,
        "Net & PE Foam" to 350.0, "Tetra Pack" to 350.0, "Accu per kg" to 3700.0, "Minyak Jelantah" to 2700.0, "Sponge/Sepatu/Sandal Bekas" to 0.0, "Tray Telur" to 50.0, "Yakult" to 450.0, "Kelapa Daksina" to 650.0, "Kulit kabel" to 100.0, "Boncos" to 550.0, "Krat" to 3250.0
    )

    // Variabel namaKategori
    private val nKategori = arrayOf("Plastik RIGD/Berbentuk", "Gelasan", "Plastik Fleksibel atau Lembaran"
        ,"Plastik Kerasan", "Kertas" , "Logam" , "Kaca" , "PET" ,"Lainnya")

    // Properti subKategoriMap
    private val subKategoriMap = mapOf(
        "Plastik RIGD/Berbentuk" to arrayOf("Putihan (HDPE & Jenis Lainnya)", "Jerigen Bening Bersih / Jerigen minyak", "Jerigen warna / bening tapi mangkak", "Tutup PET (HDPE)", "Tutup Galon (LDPE)", "Tutup Campur", "PP Emberan Campur","PP Emberan Warna", "PP Emberan Hitam", "PC Galon UTUH / BIJI", "PC Galon PECAH / KG"),
        "Gelasan" to arrayOf("Aqua Gelas Kotor", "Aqua Gelas Bersih", "Mountea/Sablon"),
        "Plastik Fleksibel atau Lembaran" to arrayOf("Plastik Bening PE & PP", "HD Kresek", "Kresek Campur", "LLDPE Pouch", "PP Sablon Lembaran", "Multi Layer"),
        "Plastik Kerasan" to arrayOf("Kerasan / PS / ABS", "PVC"),
        "Kertas" to arrayOf("Buku Tulis/Buku Pelajaran", "HVS", "Kertas Buram/LKS/warna", "Majalah", "Koran", "Duplek", "Kardus", "Kertas Campur"),
        "Logam" to arrayOf("Besi Kaleng (Omplong)", "Besi B (Tipis)", "Besi A (Tebal & Padat)", "Seng / paku", "Aluminium Kaleng/tipis", "Aluminium Tebal", "Tembaga", "Logam Campur"),
        "Kaca" to arrayOf("Botol Bir Bintang Besar/biji", "Botol Bir Bintang Kecil/biji", "Botol Kaca Campur"),
        "PET" to arrayOf("Rongsok Campur", "PET KW 2", "PET Bersih Bening", "PET Bersih Biru Muda", "PET Bersih Warna", "PET Bersih Campur"),
        "Lainnya" to arrayOf("Net & PE Foam", "Tetra Pack", "Accu per kg", "Minyak Jelantah", "Sponge/Sepatu/Sandal Bekas", "Tray Telur", "Yakult", "Kelapa Daksina", "Kulit kabel", "Boncos", "Krat")
    )

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
        judulRekening = view.findViewById(R.id.judulRekening)
        rekening = view.findViewById(R.id.rekening)
        btnPlus = view.findViewById(R.id.btnPlus)
        btnMinus = view.findViewById(R.id.btnMinus)
        btnBersihkan = view.findViewById(R.id.buttonBersihkan)
        btnKirim = view.findViewById(R.id.btnKirim)
        plus = view.findViewById(R.id.plus)
        minus = view.findViewById(R.id.minus)
        dataSampahLayout = view.findViewById(R.id.list)


        updateTextView()

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { View, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        val btnOvo = view.findViewById<LinearLayout>(R.id.btnOvo)
        val btnDana = view.findViewById<LinearLayout>(R.id.btnDana)
        val btnGopay = view.findViewById<LinearLayout>(R.id.btnGopay)
        val btnBni = view.findViewById<LinearLayout>(R.id.btnBni)
        val btnBri = view.findViewById<LinearLayout>(R.id.btnBri)
        val btnBca = view.findViewById<LinearLayout>(R.id.btnBca)
        btnOvo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isSwitchActive) {
                    btnOvo.setBackgroundResource(R.drawable.active)
                    btnDana.setBackgroundResource(R.drawable.inactive)
                    btnGopay.setBackgroundResource(R.drawable.inactive)
                    btnBni.setBackgroundResource(R.drawable.inactive)
                    btnBri.setBackgroundResource(R.drawable.inactive)
                    btnBca.setBackgroundResource(R.drawable.inactive)
                    isSwitchActive = true
                    pembayaran = "Ovo"
                    judulRekening.text = "Masukkan No Telepon"
                    rekening.hint = "Masukan No Telepon. . ."

                } else {
                    isSwitchActive = false
                }
            }
        })
        btnDana.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isSwitchActive) {
                    btnDana.setBackgroundResource(R.drawable.active)
                    btnOvo.setBackgroundResource(R.drawable.inactive)
                    btnGopay.setBackgroundResource(R.drawable.inactive)
                    btnBni.setBackgroundResource(R.drawable.inactive)
                    btnBri.setBackgroundResource(R.drawable.inactive)
                    btnBca.setBackgroundResource(R.drawable.inactive)
                    isSwitchActive = true
                    pembayaran = "Dana"
                    judulRekening.text = "Masukkan No Telepon"
                    rekening.hint = "Masukan No Telepon. . ."
                } else {
                    isSwitchActive = false
                }
            }
        })

        btnGopay.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isSwitchActive) {
                    btnGopay.setBackgroundResource(R.drawable.active)
                    btnOvo.setBackgroundResource(R.drawable.inactive)
                    btnDana.setBackgroundResource(R.drawable.inactive)
                    btnBni.setBackgroundResource(R.drawable.inactive)
                    btnBri.setBackgroundResource(R.drawable.inactive)
                    btnBca.setBackgroundResource(R.drawable.inactive)
                    isSwitchActive = true
                    pembayaran = "Gopay"
                    judulRekening.text = "Masukkan No Telepon"
                    rekening.hint = "Masukan No Telepon. . ."
                } else {
                    isSwitchActive = false
                }
            }
        })

        btnBni.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isSwitchActive) {
                    btnBni.setBackgroundResource(R.drawable.active)
                    btnOvo.setBackgroundResource(R.drawable.inactive)
                    btnDana.setBackgroundResource(R.drawable.inactive)
                    btnGopay.setBackgroundResource(R.drawable.inactive)
                    btnBri.setBackgroundResource(R.drawable.inactive)
                    btnBca.setBackgroundResource(R.drawable.inactive)
                    isSwitchActive = true
                    pembayaran = "BNI"
                    judulRekening.text = "Masukkan No Rekening"
                    rekening.hint = "Masukan No Rekening. . ."
                } else {
                    isSwitchActive = false
                }
            }
        })

        btnBri.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isSwitchActive) {
                    btnBri.setBackgroundResource(R.drawable.active)
                    btnOvo.setBackgroundResource(R.drawable.inactive)
                    btnDana.setBackgroundResource(R.drawable.inactive)
                    btnGopay.setBackgroundResource(R.drawable.inactive)
                    btnBni.setBackgroundResource(R.drawable.inactive)
                    btnBca.setBackgroundResource(R.drawable.inactive)
                    isSwitchActive = true
                    pembayaran = "BRI"
                    judulRekening.text = "Masukkan No Rekening"
                    rekening.hint = "Masukan No Rekening. . ."
                } else {
                    isSwitchActive = false
                }
            }
        })

        btnBca.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isSwitchActive) {
                    btnBca.setBackgroundResource(R.drawable.active)
                    btnOvo.setBackgroundResource(R.drawable.inactive)
                    btnDana.setBackgroundResource(R.drawable.inactive)
                    btnGopay.setBackgroundResource(R.drawable.inactive)
                    btnBni.setBackgroundResource(R.drawable.inactive)
                    btnBri.setBackgroundResource(R.drawable.inactive)
                    isSwitchActive = true
                    pembayaran = "BCA"
                    judulRekening.text = "Masukkan No Rekening"
                    rekening.hint = "Masukan No Rekening. . ."
                } else {
                    isSwitchActive = false
                }
            }
        })

        btnDatePicker.setOnClickListener {
            DatePickerDialog(requireActivity(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnPlus.setOnClickListener {
            currentValue = currentValue.add(BigDecimal("0.1"))
            updateTextView()
        }

        btnMinus.setOnClickListener {
            if (currentValue > BigDecimal("0.1")) {
                currentValue = currentValue.subtract(BigDecimal("0.1"))
                updateTextView()
            }
        }

        btnBersihkan.setOnClickListener {
            currentValue = BigDecimal("1.0")
            updateTextView()
            tvDatePicker.text = null
            namaBank.text = null
            namaPetugas.text = null
            namaNasabah.text = null
            username.text = null
            rekening.text = null
            isSwitchActive = false
            btnOvo.setBackgroundResource(R.drawable.inactive)
            btnDana.setBackgroundResource(R.drawable.inactive)
            btnGopay.setBackgroundResource(R.drawable.inactive)
            btnBni.setBackgroundResource(R.drawable.inactive)
            btnBri.setBackgroundResource(R.drawable.inactive)
            btnBca.setBackgroundResource(R.drawable.inactive)
        }

        btnKirim.setOnClickListener {
            val jumlah = number.text.toString().toDoubleOrNull() ?: 0.0
            val subtotal = hargaSubKategori?.times(jumlah) ?: 0.0
            dataTransaksi = DataTransaksi(
                namaBank = namaBank.text.toString().trim(),
                namaPetugas = namaPetugas.text.toString().trim(),
                namaNasabah = namaNasabah.text.toString().trim(),
                tanggal = tvDatePicker.text.toString().trim(),
                username = username.text.toString().trim(),
                rekening = rekening.text.toString().trim(),
                pembayaran = pembayaran,
                kategori = selectedKategori,
                subkategori = selectedSubKategori,
                jumlah = jumlah,
                hargaSubKategori = hargaSubKategori,
                subtotal = subtotal
            )
            var isAllFieldsFilled = true
            val inputText1 = namaBank.text.toString().trim()
            val inputText2 = namaPetugas.text.toString().trim()
            val inputText3 = namaNasabah.text.toString().trim()
            val inputText4 = username.text.toString().trim()
            val inputText5 = rekening.text.toString().trim()
            val inputDate = tvDatePicker.text.toString().trim()
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
            if (inputDate.isEmpty()) {
                tvDatePicker.error = "Kolom harus diisi!"
                isAllFieldsFilled = false
            }
            if (!isSwitchActive) {
                rekening.error = "Pilih Metode Transaksi Terlebih Dahulu"
            } else {
                if (isSwitchActive && rekening.text.isNullOrBlank()) {
                    rekening.error = "Masukkan No Telepon atau No Rekening tergantung dari metode pembayaran"
                } else if (isAllFieldsFilled) {
                    val intent = Intent(activity, cekData::class.java)
                    intent.putExtra("dataTransaksi", dataTransaksi)
                    startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY)
                }
            }
        }

        plus!!.setOnClickListener{ addLayout() }
        minus!!.setOnClickListener{ removeLastLayout() }


        // Dropdown menu
        // Inisialisasi Spinner
        val spinnerKategori = view.findViewById<Spinner>(R.id.spinner3)
        val arrayAdapterKategori = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, nKategori)
        spinnerKategori.adapter = arrayAdapterKategori
        spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedKategori = nKategori[position]
                updateSubKategoriSpinner(selectedKategori)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        return view
    }

    private fun updateSubKategoriSpinner(selectedKategori: String) {
        spinner2 = view?.findViewById(R.id.spinner2) ?: return
        val subKategoriArray = subKategoriMap[selectedKategori]
        if (subKategoriArray != null) {
            val arrayAdapterSubKategori = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, subKategoriArray)
            spinner2.adapter = arrayAdapterSubKategori
            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedSubKategori = subKategoriMap[selectedKategori]?.get(position) ?: ""
                    hargaSubKategori = hargaSubKategoriMap[selectedSubKategori] ?: 0.0
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
            }
        }
    }

    private fun updateTextView(layoutIndex: Int? = null) {
        val numberTextView = if (layoutIndex != null) {
            dataSampahLayout?.getChildAt(layoutIndex)?.findViewById<TextView>(R.id.number)
        } else {
            number
        }
        numberTextView?.text = currentValue.toString()
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
        tvDatePicker.error = null
    }

    private fun addLayout() {
        val inflater = layoutInflater
        val newLayout = inflater.inflate(R.layout.datasampah, null)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        newLayout.layoutParams = layoutParams
        val layoutContainer = view?.findViewById<LinearLayout>(R.id.list)
        layoutContainer?.addView(newLayout)
        val newSelectedSubKategoriList: MutableList<String> = mutableListOf()
        updateSubKategoriSpinner(selectedKategori, newSelectedSubKategoriList, newLayout)
        val newCurrentValue = BigDecimal("1.0")
        currentValueList.add(newCurrentValue)
        val newLayoutData = DataTransaksi.LayoutData(
            selectedKategori = selectedKategori,
            selectedSubKategori = selectedSubKategori,
            jumlah = currentValue.toDouble(),
            hargaSubKategori = hargaSubKategori,
            subtotal = hargaSubKategori?.times(currentValue.toDouble()) ?: 0.0
        )
        dataTransaksi = dataTransaksi.copy(
            layoutDataList = dataTransaksi.layoutDataList + newLayoutData
        )
//        spinner kategori
        val spinnerKategori = newLayout.findViewById<Spinner>(R.id.spinnerData)
        val arrayAdapterKategori = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, nKategori)
        spinnerKategori.adapter = arrayAdapterKategori
        spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedKategori = nKategori[position]
                updateSubKategoriSpinner(selectedKategori, newSelectedSubKategoriList, newLayout)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
//        spinner sub kategori
        val spinner2 = newLayout.findViewById<Spinner>(R.id.spinnerSampah)
        val subKategoriArray = subKategoriMap[selectedKategori]
        if (subKategoriArray != null) {
            val arrayAdapterSubKategori = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, subKategoriArray)
            spinner2.adapter = arrayAdapterSubKategori
            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedSubKategori = subKategoriMap[selectedKategori]?.get(position) ?: ""
                    hargaSubKategori = hargaSubKategoriMap[selectedSubKategori] ?: 0.0
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
        val number = newLayout.findViewById<TextView>(R.id.number)
        val btnPlus = newLayout.findViewById<Button>(R.id.btnPlus)
        val btnMinus = newLayout.findViewById<Button>(R.id.btnMinus)
        var currentValue = BigDecimal("1.0")

        // Mengatur teks awal untuk number
        number.text = currentValue.toString()

        // Fungsi untuk btnPlus
        btnPlus.setOnClickListener {
            currentValue = currentValue.add(BigDecimal("0.1"))
            number.text = currentValue.toString()
        }

        // Fungsi untuk btnMinus
        btnMinus.setOnClickListener {
            if (currentValue > BigDecimal("0.1")) {
                currentValue = currentValue.subtract(BigDecimal("0.1"))
                number.text = currentValue.toString()
            }
        }
    }

    private fun updateSubKategoriSpinner(selectedKategori: String, selectedSubKategoriList: MutableList<String>, newLayout: View) {
        val spinner2 = newLayout.findViewById<Spinner>(R.id.spinnerSampah)
        val subKategoriArray = subKategoriMap[selectedKategori]

        if (subKategoriArray != null) {
            val arrayAdapterSubKategori = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, subKategoriArray)
            spinner2.adapter = arrayAdapterSubKategori
            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedSubKategori = subKategoriMap[selectedKategori]?.get(position) ?: ""
                    val hargaSubKategori = hargaSubKategoriMap[selectedSubKategori] ?: 0.0
                    selectedSubKategoriList.clear()
                    selectedSubKategoriList.add(selectedSubKategori)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
    private fun removeLastLayout() {
        val layoutContainer = view?.findViewById<LinearLayout>(R.id.list)
        val childCount = layoutContainer?.childCount

        if (childCount != null && childCount > 0) {
            layoutContainer.removeViewAt(childCount - 1)
            if (currentValueList.isNotEmpty()) {
                currentValueList.removeAt(currentValueList.size - 1)
            }
        }
        updateTextView()
    }
}