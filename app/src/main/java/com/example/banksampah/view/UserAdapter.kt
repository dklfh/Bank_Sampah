package com.example.banksampah.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.R
import com.example.banksampah.model.UserData
import com.google.gson.Gson
import android.util.Log



class UserAdapter(val c: Context, val userList: ArrayList<UserData>,var backupList: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView
        val button_hapus_satuan: Button
        val buttonEditSatuan: Button
        init {
            name = v.findViewById<TextView>(R.id.mtitle)
            button_hapus_satuan = v.findViewById<Button>(R.id.button_hapus_satuan)
            buttonEditSatuan = v.findViewById<Button>(R.id.button_edit)

            button_hapus_satuan.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showDeleteDialog(position)
                }
            }

            buttonEditSatuan.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showEditDialog(position)
                }
            }
        }
    }

    private fun showEditDialog(position: Int) {
        val builder = AlertDialog.Builder(c, R.style.AppTheme_Dialog)
        val inflater = LayoutInflater.from(c)
        val dialogLayout = inflater.inflate(R.layout.edit_satuan, null)
        val editTextNamaSatuan = dialogLayout.findViewById<TextView>(R.id.mtitlesat_edit)

        // Set initial text from the item in the list
        editTextNamaSatuan.text = userList[position].userName

        val buttonBatal = dialogLayout.findViewById<Button>(R.id.button_batalsat)
        val buttonSimpan = dialogLayout.findViewById<Button>(R.id.button_simpansat)

        builder.setView(dialogLayout)
        val dialog = builder.create()

        buttonBatal.setOnClickListener {
            dialog.dismiss()
        }

        buttonSimpan.setOnClickListener {
            // Update the item in the list with the edited text
            val updatedText = editTextNamaSatuan.text.toString()
            userList[position].userName = updatedText
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.list_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.name.text = user.userName
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
        if (position >= 0 && position < userList.size) {
            val deletedUser = userList[position]
            userList.removeAt(position)
            backupList.remove(deletedUser)
            notifyItemRemoved(position)
            saveData()
            Toast.makeText(c, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
        } else {
            Log.e("UserAdapter", "Invalid position: $position")
        }
    }



    private fun saveData() {
        val sharedPreferences = c.getSharedPreferences("user_prefs_datasatuann", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(userList)
        editor.putString("user_list", json)
        val jsonBackup = gson.toJson(backupList)
        editor.putString("backup_list", jsonBackup)
        editor.apply()
    }


}