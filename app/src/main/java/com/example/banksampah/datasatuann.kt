package com.example.banksampah

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.model.UserData
import com.example.banksampah.view.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale
import androidx.appcompat.widget.SearchView


class datasatuann : Fragment() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recy: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    private lateinit var search: SearchView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var backupList: ArrayList<UserData>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_datasatuann, container, false)
        search = rootView.findViewById(R.id.search)
        addsBtn = rootView.findViewById(R.id.addingbutton)
        recy = rootView.findViewById(R.id.recyler)
        userList = ArrayList()
        backupList = ArrayList(userList)
        userAdapter = UserAdapter(requireActivity(), userList, backupList)
        recy.layoutManager = LinearLayoutManager(requireActivity())
        recy.adapter = userAdapter
        addsBtn.setOnClickListener { addInfo() }

        sharedPreferences = requireActivity().getSharedPreferences("user_prefs_datasatuann", Context.MODE_PRIVATE)

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
                    val filteredList = backupList.filter {
                        it.userName?.toLowerCase(Locale.getDefault())?.contains(searchText) ?: false && !it.isDeleted
                    }
                    userList.addAll(filteredList)
                }

                userAdapter.notifyDataSetChanged()
                return true
            }
        })

        return rootView
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(requireActivity())
        val v = inflter.inflate(R.layout.add_item, null)
        val userName = v.findViewById<EditText>(R.id.username)
        val addDialog = AlertDialog.Builder(requireActivity(), R.style.AppTheme_Dialog)
        val okButton = v.findViewById<Button>(R.id.ok_item)
        val cancelButton = v.findViewById<Button>(R.id.cancel_item)

        addDialog.setView(v)
        val alertDialog = addDialog.create()
        okButton.setOnClickListener {
            val name = userName.text.toString()

            if (name.isNotEmpty()) {
                userList.add(UserData(name))
                backupList.add(UserData("$name"))
                userAdapter.notifyDataSetChanged()
                saveData()
                alertDialog.dismiss()
                Toast.makeText(requireActivity(), "Adding User Information Success", Toast.LENGTH_SHORT).show()
            } else {
                userName.error = "Kolom harus diisi!"
                okButton.requestLayout()
                Toast.makeText(requireActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show()
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

        // Simpan daftar satuan ke dalam file JSON
        val satuanList = userAdapter.getSatuanList()
        val satuanJson = gson.toJson(satuanList)
        editor.putString("satuan_list", satuanJson)
        editor.apply()
    }

    private fun loadData() {
        val gson = Gson()
        val json = sharedPreferences.getString("user_list", null)
        val type = object : TypeToken<ArrayList<UserData>>() {}.type
        userList.clear()
        if (!json.isNullOrBlank()) {
            userList.addAll(gson.fromJson(json, type))
        }
        backupList = ArrayList(userList)
        userAdapter.notifyDataSetChanged()
    }
}

