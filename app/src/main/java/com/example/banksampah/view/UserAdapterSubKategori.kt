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

class UserAdapterSubKategori(val c: Context, val userList: ArrayList<UserDataSubKategori>, var backupList: ArrayList<UserDataSubKategori>) :
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
            val editTextNamaKategori = dialogLayout.findViewById<EditText>(R.id.namaKategori)
            val editTextNamaSubKategori = dialogLayout.findViewById<EditText>(R.id.NamaSubKategori)
            val editTextSatuan = dialogLayout.findViewById<EditText>(R.id.Satuan)
            val editTextHarga = dialogLayout.findViewById<EditText>(R.id.Harga)
            val editTextKeterangan = dialogLayout.findViewById<EditText>(R.id.Keterangan)

            // Set initial text from the item in the list
            editTextNamaKategori.setText(userList[position].UserNameSubKat)
            editTextNamaSubKategori.setText(userList[position].NamaSubKategori)
            editTextSatuan.setText(userList[position].SatuanSubKategori)
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
                // Update the item in the list with the edited text
                val updatedNamaKategori = editTextNamaKategori.text.toString()
                val updatedNamaSubKategori = editTextNamaSubKategori.text.toString()
                val updatedSatuan = editTextSatuan.text.toString()
                val updatedHarga = editTextHarga.text.toString()
                val updatedKeterangan = editTextKeterangan.text.toString()

                if (updatedNamaKategori.isNotEmpty() &&
                    updatedNamaSubKategori.isNotEmpty() &&
                    updatedSatuan.isNotEmpty() &&
                    updatedHarga.isNotEmpty() &&
                    updatedKeterangan.isNotEmpty()
                ) {
                    // Update the item in the list with the edited text
                    userList[position].UserNameSubKat = updatedNamaKategori
                    userList[position].NamaSubKategori = updatedNamaSubKategori
                    userList[position].SatuanSubKategori = updatedSatuan
                    userList[position].HargaSubKategori = updatedHarga
                    userList[position].MasukanKeteranganSubKategori = updatedKeterangan

                    saveData()
                    notifyItemChanged(position)
                    (dialogLayout.parent as? ViewGroup)?.removeView(dialogLayout)
                    dialog.dismiss()
                    Toast.makeText(c, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
                } else {
                    // Set error for each field that is empty
                    if (updatedNamaKategori.isEmpty()) {
                        editTextNamaKategori.error = "Nama Kategori tidak boleh kosong"
                    }
                    if (updatedNamaSubKategori.isEmpty()) {
                        editTextNamaSubKategori.error = "Nama Subkategori tidak boleh kosong"
                    }
                    if (updatedSatuan.isEmpty()) {
                        editTextSatuan.error = "Satuan tidak boleh kosong"
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
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteUser(position: Int) {
        val deletedItem = userList[position]
        userList.removeAt(position)
        backupList.remove(deletedItem)
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
        val json = gson.toJson(userList)
        editor.putString("user_list", json)
        val jsonBackup = gson.toJson(backupList)
        editor.putString("backup_list", jsonBackup)
        editor.apply()
    }
}