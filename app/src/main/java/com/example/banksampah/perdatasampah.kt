package com.example.banksampah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Spinner
import androidx.fragment.app.Fragment
import android.content.Context

interface OnPerdataSampahEventListener {
    fun onBatalClicked()
}

class PerdataSampah : Fragment() {
    private lateinit var eventListener: OnPerdataSampahEventListener

    fun setEventListener(listener: OnPerdataSampahEventListener) {
        eventListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.perdatasampah, container, false)

        val batalButton = view.findViewById<Button>(R.id.cancel)

        batalButton.setOnClickListener {
            eventListener.onBatalClicked()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPerdataSampahEventListener) {
            eventListener = context
        } else {
            throw RuntimeException("$context must implement OnPerdataSampahEventListener")
        }
    }
}
