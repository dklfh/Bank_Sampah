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
import com.example.banksampah.model.UserData
import com.example.banksampah.view.UserAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class datasatuann : Fragment() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recy:RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter:UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_datasatuann, container, false)
        addsBtn= rootView.findViewById(R.id.addingbutton)
        recy= rootView.findViewById(R.id.recyler)
        userList= ArrayList()
        userAdapter=UserAdapter(requireActivity(),userList)
        recy.layoutManager=LinearLayoutManager(requireActivity())
        recy.adapter=userAdapter
        addsBtn.setOnClickListener { addInfo() }



        return rootView
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun addInfo() {
        val inflter = LayoutInflater.from(requireActivity())
        val v = inflter.inflate(R.layout.add_item, null)
        val userName = v.findViewById<EditText>(R.id.userName)
        val addDialog = AlertDialog.Builder(requireActivity())


        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val name = userName.text.toString()

            userList.add(UserData("$name"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(requireActivity(),"Adding User Information Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(requireActivity(),"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()

    }
}