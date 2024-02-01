package com.example.banksampah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.model.UserData
import com.example.banksampah.view.UserAdapter
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



        return rootView
    }
    private fun addinfo(){
        val inflter=LayoutInflater.from(requireActivity())
        val v =inflter.inflate(R.layout.add_item,null)
        val userName=v.findViewById<EditText>(R.id.mtitle)

    }
}