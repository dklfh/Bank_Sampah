package com.example.banksampah.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.R
import com.example.banksampah.model.UserDataSubKategori
import com.example.banksampah.view.UserAdapter

class UserAdapterSubKategori(val c: Context, val userList:ArrayList<UserDataSubKategori>):RecyclerView.Adapter<UserAdapterSubKategori.UserViewHolder>() {

    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var satuan: TextView
        var namasub: TextView
        var satuansub: TextView
        var hargasub: TextView
        var keterangansub: TextView
        val buttonHapus: Button

        init {
            satuan = v.findViewById<TextView>(R.id.mtitlesubkategori)
            namasub = v.findViewById<TextView>(R.id.MtitleNamaSubKategori)
            satuansub = v.findViewById<TextView>(R.id.MtitleSatuanSubKategori)
            hargasub = v.findViewById<TextView>(R.id.MtitlrHargaSubKategori)
            keterangansub = v.findViewById<TextView>(R.id.MtitleKeteranganSubKategori)
            buttonHapus = v.findViewById<Button>(R.id.button_hapus_subkategori)

            buttonHapus.setOnClickListener {
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
        val v  = inflater.inflate(R.layout.list_item_subkategori,parent,false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.satuan.text = newList.UserNameSubKat
        holder.namasub.text = newList.NamaSubKategori
        holder.satuansub.text = newList.SatuanSubKategori
        holder.hargasub.text = newList.HargaSubKategori
        holder.keterangansub.text = newList.MasukanKeteranganSubKategori
    }
}

