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
import com.google.gson.reflect.TypeToken
import java.io.*


class datasubkategorii : Fragment() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recy: RecyclerView
    private lateinit var userList: ArrayList<UserDataSubKategori>
    private lateinit var userAdapterSubKategori: UserAdapterSubKategori
    private lateinit var search: SearchView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_datasubkategorii, container, false)
        search = rootView.findViewById(R.id.searchsubkategori)
        addsBtn = rootView.findViewById(R.id.addingbuttonsubkategori)
        recy = rootView.findViewById(R.id.recylersubkategori)
        userList = ArrayList()
        userAdapterSubKategori = UserAdapterSubKategori(requireActivity(), userList)
        recy.layoutManager = LinearLayoutManager(requireActivity())
        recy.adapter = userAdapterSubKategori
        addsBtn.setOnClickListener { addInfo() }

        sharedPreferences = requireActivity().getSharedPreferences("user_prefs_datasubkategori",Context.MODE_PRIVATE)

        loadData()

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText?.lowercase(Locale.getDefault()) ?: ""
                userList.clear()
                if (searchText.isNotEmpty()) {
                    userList.forEach {
                        if (it.UserNameSubKat.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            userList.add(it)
                        }
                    }
                    recy.adapter!!.notifyDataSetChanged()
                } else {
                    userList.clear()
                    userList.addAll(userList)
                    recy.adapter!!.notifyDataSetChanged()
                }
                return true
            }
        })

        return rootView
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addInfo() {
        val inflter = LayoutInflater.from(requireActivity())
        val v = inflter.inflate(R.layout.add_item_subkategori, null)
        val namesubkat = v.findViewById<EditText>(R.id.UserNameSubKat)
        val namesubkategori = v.findViewById<EditText>(R.id.NamaSubKategori)
        val SatuanSubKategori = v.findViewById<EditText>(R.id.SatuanSubKategori)
        val HargaSubKategori = v.findViewById<EditText>(R.id.HargaSubKategori)
        val MasukanKeteranganSubKategori = v.findViewById<EditText>(R.id.MasukanKeteranganSubKategori)
        val addDialog = AlertDialog.Builder(requireActivity(),R.style.AppTheme_Dialog)
        val okButton = v.findViewById<Button>(R.id.ok_itemsubkat)
        val cancelButton = v.findViewById<Button>(R.id.cancel_itemsubkat)




        addDialog.setView(v)
        val alertDialog = addDialog.create()
        okButton.setOnClickListener {
            val namesubkat= namesubkat.text.toString()
            val namesubkategori= namesubkategori .text.toString()
            val satuansubkategori= SatuanSubKategori.text.toString()
            val hargasubkategori= HargaSubKategori.text.toString()
            val masukanketerangansubkategori= MasukanKeteranganSubKategori.text.toString()

            if (namesubkat.isNotEmpty()) {
                userList.add(UserDataSubKategori("$namesubkat","$namesubkategori","$satuansubkategori","$hargasubkategori","$masukanketerangansubkategori"))
                userAdapterSubKategori.notifyDataSetChanged()
                saveData()
                alertDialog.dismiss()
                Toast.makeText(requireActivity(), "Adding User Information Success", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(requireActivity(),"Adding User Information Success",Toast.LENGTH_SHORT).show()
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
        userAdapterSubKategori.notifyDataSetChanged()
    }
}

