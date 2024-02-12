package com.example.banksampah.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.R
import com.example.banksampah.model.UserData

class UserAdapter(val c: Context, val userList:ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{

    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        val button_hapus = v.findViewById<Button>(R.id.button_hapus)

        init {
            name = v.findViewById<TextView>(R.id.mtitle)
            button_hapus.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    userList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

    }

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
