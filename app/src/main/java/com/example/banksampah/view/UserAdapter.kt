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

class UserAdapter(val c: Context, val userList:ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{

    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        val button_hapus_satuan : Button

        init {
            name = v.findViewById<TextView>(R.id.mtitle)
            button_hapus_satuan = v.findViewById<Button>(R.id.button_hapus_satuan)

            button_hapus_satuan.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showDeleteDialog(position)
                }
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
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.userName
    }

    override fun getItemCount(): Int {
        return  userList.size
    }

}
