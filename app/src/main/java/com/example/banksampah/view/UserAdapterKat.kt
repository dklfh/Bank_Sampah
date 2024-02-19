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
import com.example.banksampah.model.UserDataKat
import com.google.gson.Gson

class UserAdapterKat(val c: Context, val userList: ArrayList<UserDataKat>) :
    RecyclerView.Adapter<UserAdapterKat.UserViewHolder>() {

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

                saveData()
                notifyItemChanged(position)
                (dialogLayout.parent as? ViewGroup)?.removeView(dialogLayout)
                dialog.dismiss()
                Toast.makeText(c, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
            }
            dialog.show()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.list_item_kategori, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.name.text = user.userNameKat
    }

    override fun getItemCount(): Int {
        return userList.size
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
        userList.removeAt(position)
        notifyItemRemoved(position)
        saveData()
        Toast.makeText(c, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
    }

    private fun saveData() {
        val sharedPreferences =
            c.getSharedPreferences("user_prefs_datakategori", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(userList)
        editor.putString("user_list", json)
        editor.apply()
    }
}