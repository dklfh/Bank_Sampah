package com.example.banksampah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.malkinfo.editingrecyclerview.model.UserData
//import com.malkinfo.editingrecyclerview.view.UserAdapter
//
//class datasubkategorii : Fragment() {
//
//    private lateinit var addsBtn: FloatingActionButton
//    private lateinit var recv: RecyclerView
//    private lateinit var userList: ArrayList<UserData>
//    private lateinit var userAdapter: UserAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_datasubkategorii, container, false)
//
//        /** Set List */
//        userList = ArrayList()
//
//        /** Set find Id */
//        addsBtn = view.findViewById(R.id.addingBtn)
//        recv = view.findViewById(R.id.recycler)
//
//        /** Set Adapter */
//        userAdapter = UserAdapter(requireContext(), userList)
//
//        /** Set Recycler view Adapter */
//        recv.layoutManager = LinearLayoutManager(requireContext())
//        recv.adapter = userAdapter
//
//        /** Set Dialog */
//        addsBtn.setOnClickListener { addInfo() }
//
//        return view
//    }
//
//    private fun addInfo() {
//        val inflter = LayoutInflater.from(requireContext())
//        val v = inflter.inflate(R.layout.add_item, null)
//
//        /** Set view */
//        val userName = v.findViewById<EditText>(R.id.userName)
//        val userNo = v.findViewById<EditText>(R.id.userNo)
//
//        val addDialog = AlertDialog.Builder(requireContext())
//
//        addDialog.setView(v)
//        addDialog.setPositiveButton("Ok") { dialog, _ ->
//            val names = userName.text.toString()
//            val number = userNo.text.toString()
//            userList.add(UserData("Name: $names", "Mobile No. : $number"))
//            userAdapter.notifyDataSetChanged()
//            Toast.makeText(requireContext(), "Adding User Information Success", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        }
//        addDialog.setNegativeButton("Cancel") { dialog, _ ->
//            dialog.dismiss()
//            Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
//
//        }
//        addDialog.create()
//        addDialog.show()
//    }
//}
