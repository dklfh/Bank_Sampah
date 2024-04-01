package com.example.banksampah

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import java.util.Calendar
import android.widget.Button

class HistoriTransaksi : Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<UserHistori>
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var loadingText: TextView
    private lateinit var textViewTanggal: TextView
    private lateinit var datePickerDialog: DatePickerDialog

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
        textViewTanggal = view.findViewById(R.id.tgl_awal)

        userArrayList = arrayListOf()
        getUserData()

        // Mendapatkan instance dari kalendar untuk menampilkan tanggal default
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Inisialisasi DatePickerDialog
        datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            // Saat pengguna memilih tanggal, atur tanggal pada TextView
            val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
            textViewTanggal.text = selectedDate

            // Panggil method untuk melakukan filter data sesuai dengan tanggal yang dipilih
            filterDataByDate(selectedDate)
        }, year, month, dayOfMonth)

        // Atur aksi saat TextView diklik
        textViewTanggal.setOnClickListener {
            // Tampilkan DatePickerDialog
            datePickerDialog.show()
        }

        view.findViewById<Button>(R.id.btn_cari).setOnClickListener {
            val selectedDate = textViewTanggal.text.toString()
            filterDataByDate(selectedDate)
        }
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
                    } else {
                        // No transaction history data, so hide the loading animation and display a message
                        loadingProgressBar.visibility = View.GONE
                        loadingText.visibility = View.GONE
                        Toast.makeText(activity, "Tidak ada data histori transaksi", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    loadingProgressBar.visibility = View.GONE
                    loadingText.visibility = View.GONE
                    // Tampilkan pesan bahwa ada kesalahan
                    Toast.makeText(activity, "Terjadi kesalahan: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun filterDataByDate(tanggal: String) {
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
                            if (user != null && user.tanggal == tanggal) {
                                newDataList.add(user)
                            }
                        }

                        if (newDataList.isEmpty()) {
                            // Tampilkan pesan bahwa tidak ada data sesuai dengan tanggal
                            Toast.makeText(activity, "Tidak ada data sesuai tanggal", Toast.LENGTH_SHORT).show()
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
                    // Tampilkan pesan bahwa ada kesalahan
                    Toast.makeText(activity, "Terjadi kesalahan: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
