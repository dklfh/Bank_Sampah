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
import com.example.banksampah.model.UserDataKat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.banksampah.view.UserAdapterKat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale
import android.content.SharedPreferences
import android.widget.Button
class datakategorii : Fragment() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recy: RecyclerView
    private lateinit var userList: ArrayList<UserDataKat>
    private lateinit var userAdapterKat: UserAdapterKat
    private lateinit var search: SearchView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var backupList: ArrayList<UserDataKat>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_datakategorii, container, false)
        search = rootView.findViewById(R.id.searchkategori)
        addsBtn = rootView.findViewById(R.id.addingbuttonkat)
        recy = rootView.findViewById(R.id.recyclerViewKat)
        userList = ArrayList()
        backupList = ArrayList(userList)
        userAdapterKat = UserAdapterKat(requireActivity(), userList, backupList)
        recy.layoutManager = LinearLayoutManager(requireActivity())
        recy.adapter = userAdapterKat
        addsBtn.setOnClickListener { addInfo() }

        sharedPreferences = requireActivity().getSharedPreferences("user_prefs_datakategori", Context.MODE_PRIVATE)

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
                        it.userNameKat?.toLowerCase(Locale.getDefault())?.contains(searchText) ?: false && !it.isDeleted
                    }
                    userList.addAll(filteredList)
                }

                userAdapterKat.notifyDataSetChanged()
                return true
            }
        })

        return rootView
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(requireActivity())
        val v = inflter.inflate(R.layout.add_item_kategori, null)
        val userNameKat = v.findViewById<EditText>(R.id.userNameKat)
        val addDialog = AlertDialog.Builder(requireActivity(), R.style.AppTheme_Dialog)
        val okButton = v.findViewById<Button>(R.id.ok_item)
        val cancelButton = v.findViewById<Button>(R.id.cancel_item)

        addDialog.setView(v)
        val alertDialog = addDialog.create()
        okButton.setOnClickListener {
            val name = userNameKat.text.toString()

            if (name.isNotEmpty()) {
                userList.add(UserDataKat("$name"))
                // Tidak perlu menyimpan backupList = ArrayList(userList) di sini
                userAdapterKat.notifyDataSetChanged()
                saveData()
                alertDialog.dismiss()
                Toast.makeText(requireActivity(), "Adding User Information Success", Toast.LENGTH_SHORT).show()
            } else {
                userNameKat.error = "Kolom harus diisi!"
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
    }

    private fun loadData() {
        val gson = Gson()
        val json = sharedPreferences.getString("user_list", null)
        val type = object : TypeToken<ArrayList<UserDataKat>>() {}.type
        userList.clear()
        if (!json.isNullOrBlank()) {
            userList.addAll(gson.fromJson(json, type))
        }
        backupList = ArrayList(userList)
        userAdapterKat.notifyDataSetChanged()
    }
}
