package com.example.banksampah

import com.example.banksampah.view.UserAdapterSubKategori
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.model.UserDataSubKategori
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale
import com.google.gson.Gson
import android.content.SharedPreferences
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.banksampah.model.UserDataKat
import com.google.gson.reflect.TypeToken
import java.io.*


class datasubkategorii : Fragment() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recy: RecyclerView
    private lateinit var userList: ArrayList<UserDataSubKategori>
    private lateinit var userAdapterSubKategori: UserAdapterSubKategori
    private lateinit var search: SearchView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var backupList: ArrayList<UserDataSubKategori>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_datasubkategorii, container, false)
        search = rootView.findViewById(R.id.searchsubkategori)
        addsBtn = rootView.findViewById(R.id.addingbuttonsubkategori)
        recy = rootView.findViewById(R.id.recylersubkategori)
        userList = ArrayList()
        backupList = ArrayList(userList)
        userAdapterSubKategori = UserAdapterSubKategori(requireActivity(), userList, backupList)
        recy.layoutManager = LinearLayoutManager(requireActivity())
        recy.adapter = userAdapterSubKategori
        addsBtn.setOnClickListener { addInfo() }

        sharedPreferences = requireActivity().getSharedPreferences("user_prefs_datasubkategori",Context.MODE_PRIVATE)

        loadData()

        backupList = ArrayList(userList)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText?.lowercase(Locale.getDefault()) ?: ""

                userList.clear()

                if (searchText.isBlank()) {
                    userList.addAll(backupList.filter { !it.isDeleted })
                } else {
                    // Filter original list based on the search text
                    val filteredList = backupList.filter {
                        it.NamaSubKategori.toLowerCase(Locale.getDefault()).contains(searchText) ?: false && !it.isDeleted
                    }
                    userList.addAll(filteredList)
                }

                userAdapterSubKategori.notifyDataSetChanged()
                return true
            }
        })

        return rootView
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addInfo() {
        val inflter = LayoutInflater.from(requireActivity())
        val v = inflter.inflate(R.layout.add_item_subkategori, null)
        val namesubkat = v.findViewById<Spinner>(R.id.UserNameSubKat)
        val namesubkategorii = v.findViewById<EditText>(R.id.NamaSubKategori)
        val SatuanSubKategori = v.findViewById<EditText>(R.id.SatuanSubKategori)
        val HargaSubKategori = v.findViewById<EditText>(R.id.HargaSubKategori)
        val MasukanKeteranganSubKategori = v.findViewById<EditText>(R.id.MasukanKeteranganSubKategori)
        val addDialog = AlertDialog.Builder(requireActivity(),R.style.AppTheme_Dialog)
        val okButton = v.findViewById<Button>(R.id.ok_itemsubkat)
        val cancelButton = v.findViewById<Button>(R.id.cancel_itemsubkat)

        val satuanOptions = arrayOf("Plastik RIGD/Berbentuk", "Gelasan", "Plastik Fleksibel atau Lembaran"
            ,"Plastik Kerasan", "Kertas" , "Logam" , "Kaca" , "PET" ,"Lainnya")

        val satuanAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, satuanOptions)

        satuanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        namesubkat.adapter = satuanAdapter

        addDialog.setView(v)
        val alertDialog = addDialog.create()
        okButton.setOnClickListener {
            val namesubkat = namesubkat.selectedItem.toString()
            val namesubkategori= namesubkategorii .text.toString()
            val satuansubkategori= SatuanSubKategori.text.toString()
            val hargasubkategori= HargaSubKategori.text.toString()
            val masukanketerangansubkategori= MasukanKeteranganSubKategori.text.toString()

            var isError = false

            if (namesubkategori.isEmpty()) {
                namesubkategorii.error = "Kolom harus diisi!"
                isError = true
            }

            if (satuansubkategori.isEmpty()) {
                SatuanSubKategori.error = "Kolom harus diisi!"
                isError = true
            }

            if (hargasubkategori.isEmpty()) {
                HargaSubKategori.error = "Kolom harus diisi!"
                isError = true
            }

            if (masukanketerangansubkategori.isEmpty()) {
                MasukanKeteranganSubKategori.error = "Kolom harus diisi!"
                isError = true
            }

            if (!isError) {
                userList.add(UserDataSubKategori("$namesubkat","$namesubkategori","$satuansubkategori","$hargasubkategori","$masukanketerangansubkategori"))
//                backupList.add(UserDataSubKategori("$namesubkat", "$namesubkategori", "$satuansubkategori", "$hargasubkategori", "$masukanketerangansubkategori"))
                backupList = ArrayList(userList)
                userAdapterSubKategori.notifyDataSetChanged()
                saveData()
                alertDialog.dismiss()
                Toast.makeText(requireActivity(), "Adding User Information Success", Toast.LENGTH_SHORT).show()
            }
        }
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun saveData() {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(userList)
        editor.putString("user_list", json)
        editor.apply()
    }

    private fun loadData() {
        val gson = Gson()
        val json = sharedPreferences.getString("user_list", null)
        val type = object : TypeToken<ArrayList<UserDataSubKategori>>() {}.type
        userList.clear()
        if (!json.isNullOrBlank()) {
            userList.addAll(gson.fromJson(json, type))
        }
        backupList = ArrayList(userList)
        userAdapterSubKategori.notifyDataSetChanged()
    }
}

