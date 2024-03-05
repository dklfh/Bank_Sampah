package com.example.banksampah.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.R
import com.example.banksampah.model.UserDataKat
import com.example.banksampah.model.UserDataSubKategori
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserAdapterSubKategori(
    val c: Context,
    val userList: ArrayList<UserDataSubKategori>,
    var backupList: ArrayList<UserDataSubKategori>
) :
    RecyclerView.Adapter<UserAdapterSubKategori.UserViewHolder>() {

    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var satuan: TextView = v.findViewById<TextView>(R.id.mtitlesubkategori)
        var namasub: TextView = v.findViewById<TextView>(R.id.MtitleNamaSubKategori)
        var satuansub: TextView = v.findViewById<TextView>(R.id.MtitleSatuanSubKategori)
        var hargasub: TextView = v.findViewById<TextView>(R.id.MtitlrHargaSubKategori)
        var keterangansub: TextView = v.findViewById<TextView>(R.id.MtitleKeteranganSubKategori)
        val button_hapus_subKategori: Button = v.findViewById<Button>(R.id.button_hapus_subkategori)
        val buttonEditSubKategori: Button = v.findViewById<Button>(R.id.button_edit)

        init {
            button_hapus_subKategori.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showDeleteDialog(position)
                }
            }
            buttonEditSubKategori.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showEditDialog(position)
                }
            }
        }

        private fun showEditDialog(position: Int) {
            val builder = AlertDialog.Builder(c, R.style.AppTheme_Dialog)
            val inflater = LayoutInflater.from(c)
            val dialogLayout = inflater.inflate(R.layout.edit_subkategori, null)
            val editTextNamaSubKategori = dialogLayout.findViewById<EditText>(R.id.NamaSubKategori)
            val spinnerKategori = dialogLayout.findViewById<Spinner>(R.id.UserNameSubKat)
            val spinnerSatuan = dialogLayout.findViewById<Spinner>(R.id.SatuanSubKategori)
            val editTextHarga = dialogLayout.findViewById<EditText>(R.id.HargaSubKategori)
            val editTextKeterangan = dialogLayout.findViewById<EditText>(R.id.MasukanKeteranganSubKategori)

            // Load data kategori dan satuan dari SharedPreferences
            val kategoriList = loadKategoriFromSharedPreferences()
            val satuanList = getSatuanList()

            // Inisialisasi adapter untuk spinner kategori dan satuan
            val kategoriAdapter = ArrayAdapter(c, android.R.layout.simple_spinner_item, kategoriList)
            val satuanAdapter = ArrayAdapter(c, android.R.layout.simple_spinner_item, satuanList)

            // Set dropdown style untuk adapter
            kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            satuanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Set adapter ke spinner
            spinnerKategori.adapter = kategoriAdapter
            spinnerSatuan.adapter = satuanAdapter

            // Set initial text from the item in the list
            editTextNamaSubKategori.setText(userList[position].NamaSubKategori)
            editTextHarga.setText(userList[position].HargaSubKategori)
            editTextKeterangan.setText(userList[position].MasukanKeteranganSubKategori)

            val buttonBatal = dialogLayout.findViewById<Button>(R.id.button_batalsub)
            val buttonSimpan = dialogLayout.findViewById<Button>(R.id.button_simpansub)

            builder.setView(dialogLayout)
            val dialog = builder.create()

            buttonBatal.setOnClickListener {
                dialog.dismiss()
            }

            buttonSimpan.setOnClickListener {
                val updatedNamaSubKategori = editTextNamaSubKategori.text.toString()
                val updatedKategori = spinnerKategori.selectedItem.toString()
                val updatedSatuan = spinnerSatuan.selectedItem.toString()
                val updatedHarga = editTextHarga.text.toString()
                val updatedKeterangan = editTextKeterangan.text.toString()

                if (updatedNamaSubKategori.isNotEmpty() && updatedHarga.isNotEmpty() && updatedKeterangan.isNotEmpty()) {
                    // Update data subkategori
                    userList[position].NamaSubKategori = updatedNamaSubKategori
                    userList[position].UserNameSubKat = updatedKategori
                    userList[position].SatuanSubKategori = updatedSatuan
                    userList[position].HargaSubKategori = updatedHarga
                    userList[position].MasukanKeteranganSubKategori = updatedKeterangan

                    // Simpan perubahan data ke SharedPreferences
                    saveData()

                    // Perbarui tampilan RecyclerView
                    notifyItemChanged(position)

                    // Tutup dialog
                    dialog.dismiss()

                    // Tampilkan pesan berhasil
                    Toast.makeText(c, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
                } else {
                    // Set error jika ada field yang kosong
                    if (updatedNamaSubKategori.isEmpty()) {
                        editTextNamaSubKategori.error = "Nama subkategori tidak boleh kosong"
                    }
                    if (updatedHarga.isEmpty()) {
                        editTextHarga.error = "Harga tidak boleh kosong"
                    }
                    if (updatedKeterangan.isEmpty()) {
                        editTextKeterangan.error = "Keterangan tidak boleh kosong"
                    }
                }
            }
            dialog.show()
        }

    }

    private fun showDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(c, R.style.AppTheme_Dialog)
        val inflater = LayoutInflater.from(c)
        val dialogLayout = inflater.inflate(R.layout.metodehapus, null)

        val buttonTidak = dialogLayout.findViewById<Button>(R.id.tidak)
        val buttonYa = dialogLayout.findViewById<Button>(R.id.ya)

        builder.setView(dialogLayout)
        val dialog = builder.create()

        buttonTidak.setOnClickListener {
            dialog.dismiss()
        }

        buttonYa.setOnClickListener {
            deleteUser(position)
            backupList = ArrayList(userList)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteUser(position: Int) {
        val deletedItem = userList[position]
        userList.removeAt(position)
        deletedItem.isDeleted = true
        backupList = ArrayList(userList)
        notifyItemRemoved(position)
        saveData()
        Toast.makeText(c, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.list_item_subkategori, parent, false)
        return UserViewHolder(view)
    }


    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.satuan.text = user.UserNameSubKat
        holder.namasub.text = user.NamaSubKategori
        holder.satuansub.text = user.SatuanSubKategori
        holder.hargasub.text = user.HargaSubKategori
        holder.keterangansub.text = user.MasukanKeteranganSubKategori
    }

    private fun saveData() {
        val sharedPreferences = c.getSharedPreferences("user_prefs_datasubkategori", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        val nonDeletedList = userList.filter { !it.isDeleted }

        val json = gson.toJson(nonDeletedList)
        editor.putString("user_list", json)

        val satuanList = getSatuanList()
        val satuanJson = gson.toJson(satuanList)
        editor.putString("satuan_list", satuanJson)
        editor.apply()
    }

    private fun getSatuanList(): ArrayList<String> {
        val satuanList = ArrayList<String>()
        for (userData in userList) {
            userData.NamaSubKategori?.let {
                satuanList.add(it)
            }
        }
        return satuanList
    }

    private fun loadKategoriFromSharedPreferences(): ArrayList<String> {
        val sharedPreferences = c.getSharedPreferences("user_prefs_datakategori", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("user_list", null)
        val type = object : TypeToken<ArrayList<UserDataKat>>() {}.type
        val userList = ArrayList<String>()

        if (!json.isNullOrBlank()) {
            val kategoriList = gson.fromJson<ArrayList<UserDataKat>>(json, type)
            kategoriList.forEach { userDataKat ->
                userDataKat.userNameKat?.let {
                    userList.add(it)
                }
            }
        }
        return userList
    }
}
