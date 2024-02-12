package com.example.banksampah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment

class datasampah : Fragment() {

    private lateinit var selectedKategori: String
    private lateinit var selectedSubKategori: String
    private var hargaSubKategori: Double = 0.0
    private lateinit var spinner2: Spinner

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

    private val nKategori = arrayOf(
        "Plastik RIGD/Berbentuk", "Gelasan", "Plastik Fleksibel atau Lembaran"
        ,"Plastik Kerasan", "Kertas" , "Logam" , "Kaca" , "PET" ,"Lainnya"
    )

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

    // Rest of your code remains the same...

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.datasampah, container, false)

        // Inisialisasi Spinner Kategori
        val spinnerKategori = view.findViewById<Spinner>(R.id.spinnerData)
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

        // Inisialisasi Spinner SubKategori
        spinner2 = view.findViewById(R.id.spinnerSampah)

        return view
    }

    private fun updateSubKategoriSpinner(selectedKategori: String) {
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
}
