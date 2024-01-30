package com.example.banksampah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class datakategorii : Fragment() {

    // Variabel player
    private val player = arrayOf("Player 1", "Player 2", "Player 3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_datakategorii, container, false)

        // Inisialisasi Spinner
        val spinner = rootView.findViewById<Spinner>(R.id.spinner1)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, player)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Not implemented
            }
        }

        return rootView
    }
}
