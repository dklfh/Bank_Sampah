package com.example.banksampah

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.util.Locale
import android.content.SharedPreferences
import android.widget.Button
import com.example.banksampah.model.UserData

import com.example.banksampah.view.UserAdapter


class datasatuann : Fragment() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recy: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    private lateinit var search: SearchView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_datasatuann, container, false)
        search = rootView.findViewById(R.id.search)
        addsBtn = rootView.findViewById(R.id.addingbutton)
        recy = rootView.findViewById(R.id.recyler)
        userList = ArrayList()
        userAdapter = UserAdapter(requireActivity(), userList)
        recy.layoutManager = LinearLayoutManager(requireActivity())
        recy.adapter = userAdapter
        addsBtn.setOnClickListener { addInfo() }

        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        loadData()

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText?.lowercase() ?: ""
                val filteredList = userList.filter {
                    it.userName.toLowerCase(Locale.getDefault()).contains(searchText)
                }
                userAdapter.userList.clear()
                userAdapter.userList.addAll(filteredList)
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
                userList.add(UserData("$name"))
                userAdapter.notifyDataSetChanged()
                saveData()
                alertDialog.dismiss()
                Toast.makeText(requireActivity(), "Adding User Information Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireActivity(), "Nam  e cannot be empty", Toast.LENGTH_SHORT).show()
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
        val type = object : TypeToken<ArrayList<UserData>>() {}.type // Mengubah menjadi UserData
        userList.clear()
        if (!json.isNullOrBlank()) {
            userList.addAll(gson.fromJson(json, type))
        }
        userAdapter.notifyDataSetChanged()
    }

}
