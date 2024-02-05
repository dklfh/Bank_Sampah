package com.example.banksampah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class PeriodeSampah : Fragment() {

    private var overlayListener: OverlayListener? = null
    private val periode = arrayOf("1 Bulan", "6 Bulan", "1 Tahun")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_periode_sampah, container, false)

        overlayListener = activity as? OverlayListener

        val batalButton: Button = view.findViewById(R.id.batal)
        batalButton.setOnClickListener {
            // Panggil metode hideOverlay() dari activity host menggunakan interface
            (activity as? OverlayListener)?.onHideOverlay()
        }

        // Inisialisasi Spinner
        val spinner: Spinner = view.findViewById(R.id.periode)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, periode)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Not implemented
            }
        }

        return view
    }

}
