package com.example.banksampah.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.R
import com.example.banksampah.model.UserData
import com.example.banksampah.model.UserDataKat

class UserAdapterKat(val c: Context, val userList: ArrayList<UserDataKat>) : RecyclerView.Adapter<UserAdapterKat.UserViewHolder>() {

    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView
        val buttonHapusKat: Button
        val buttonEditKat: Button

        init {
            name = v.findViewById<TextView>(R.id.mtitlekat)
            buttonHapusKat = v.findViewById<Button>(R.id.button_hapus_kat)
            buttonEditKat = v.findViewById<Button>(R.id.button_editkat)

            buttonHapusKat.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showDeleteDialog(position)
                }
            }

            buttonEditKat.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showEditDialog(position)
                }
            }
        }

        private fun showEditDialog(position: Int) {
            val builder = AlertDialog.Builder(c, R.style.AppTheme_Dialog)
            val inflater = LayoutInflater.from(c)
            val dialogLayout = inflater.inflate(R.layout.edit_kategori, null)

            val editTextKategori = dialogLayout.findViewById<EditText>(R.id.mtitlekat_edit)
            val editTextNamaKategori = dialogLayout.findViewById<TextView>(R.id.mtitlekat_edit)

            // Set initial text from the item in the list
            editTextNamaKategori.text = userList[position].userNameKat

            val buttonBatal = dialogLayout.findViewById<Button>(R.id.button_batal_kat)
            val buttonSimpan = dialogLayout.findViewById<Button>(R.id.button_simpankat)

            builder.setView(dialogLayout)
            val dialog = builder.create()

            buttonBatal.setOnClickListener {
                dialog.dismiss()
            }

            buttonSimpan.setOnClickListener {
                // Update the item in the list with the edited text
                val updatedText = editTextNamaKategori.text.toString()
                userList[position].userNameKat = updatedText
                userList[position].isModified = true
                userList[position].modifiedText = updatedText

                notifyItemChanged(position)
                (dialogLayout.parent as? ViewGroup)?.removeView(dialogLayout)
                dialog.dismiss()
                Toast.makeText(c, "Data berhasil diubah", Toast.LENGTH_SHORT).show()

                // Simpan data lokal secara permanen jika diperlukan
                // Misalnya, menyimpan dalam database SQLite atau berkas lokal
                // Contoh penyimpanan sederhana di dalam UserAdapterKat
                saveDataLocally(userList)
            }
            dialog.show()
        }
    }

    private fun saveDataLocally(userList: List<UserDataKat>) {
        // Kirim data ke server atau simpan ke database lokal di sini
        // Contoh sederhana, menyimpan ke server
        for (user in userList) {
            if (user.isModified) {
                // Kirim data ke server
                // Misalnya: ApiService.updateUserData(user.userNameKat, user.modifiedText)
            }
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
            userList.removeAt(position)
            notifyItemRemoved(position)
            (dialogLayout.parent as? ViewGroup)?.removeView(dialogLayout)
            dialog.dismiss()
            Toast.makeText(c, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item_kategori, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.userNameKat
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}