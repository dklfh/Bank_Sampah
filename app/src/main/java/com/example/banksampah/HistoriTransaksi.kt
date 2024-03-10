package com.example.banksampah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.model.UserHistori
import com.example.banksampah.view.historiadapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoriTransaksi : Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<UserHistori>
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var loadingText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_histori_transaksi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRecyclerView = view.findViewById(R.id.userList)
        userRecyclerView.layoutManager = LinearLayoutManager(activity)
        userRecyclerView.setHasFixedSize(true)
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        loadingText = view.findViewById(R.id.loadingText)

        userArrayList = arrayListOf()
        getUserData()
    }

    private fun getUserData() {
        loadingProgressBar.visibility = View.VISIBLE
        loadingText.visibility = View.VISIBLE

        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userId ->
            dbref = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Data transaksi")
            dbref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val newDataList = ArrayList<UserHistori>()

                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(UserHistori::class.java)
                            if (user != null && !userArrayList.contains(user)) {
                                newDataList.add(user)
                            }
                        }
                        userArrayList.clear()
                        userArrayList.addAll(newDataList)

                        userRecyclerView.adapter = historiadapter(userArrayList)

                        loadingProgressBar.visibility = View.GONE
                        loadingText.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    loadingProgressBar.visibility = View.GONE
                    loadingText.visibility = View.GONE
                }
            })
        }
    }

}