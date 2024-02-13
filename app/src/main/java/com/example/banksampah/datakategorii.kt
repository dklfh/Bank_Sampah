package com.example.banksampah

import android.annotation.SuppressLint
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
import java.util.Locale

class datakategorii : Fragment() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recy: RecyclerView
    private lateinit var userList: ArrayList<UserDataKat>
    private lateinit var userAdapter: UserAdapterKat
    private lateinit var search: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_datakategorii, container, false)
        search = rootView.findViewById(R.id.searchkategori)
        addsBtn = rootView.findViewById(R.id.addingbuttonkat)
        recy = rootView.findViewById(R.id.recyclerViewKat)
        userList = ArrayList()
        userAdapter = UserAdapterKat(requireActivity(), userList)
        recy.layoutManager = LinearLayoutManager(requireActivity())
        recy.adapter = userAdapter
        addsBtn.setOnClickListener { addInfo() }


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
                        if (it.userNameKat.toLowerCase(Locale.getDefault()).contains(searchText)) {
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
        val v = inflter.inflate(R.layout.add_item_kategori, null)
        val userName = v.findViewById<EditText>(R.id.userNameKat)
        val addDialog = AlertDialog.Builder(requireActivity())


        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val name = userName.text.toString()

            userList.add(UserDataKat("$name"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(requireActivity(), "Adding User Information Success", Toast.LENGTH_SHORT)
                .show()
            dialog.dismiss()

        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()

            Toast.makeText(requireActivity(),"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()


        }
    }

