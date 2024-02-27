package com.example.banksampah.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.R
import com.example.banksampah.model.UserHistori
import kotlin.contracts.Returns

class historiadapter (private val userList : ArrayList<UserHistori>) : RecyclerView.Adapter<historiadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_historitransaksi, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem=userList[position]
        holder.Tanggalhistori.text=currentitem.tanggalhistori
        holder.NamaBank.text=currentitem.namaBank
        holder.NamaNasabah.text=currentitem.namaNasabah
        holder.NamaPetugas.text=currentitem.namaPetugas
        holder.MetodPembayaran.text=currentitem.pembayaran
        holder.Rekening.text=currentitem.rekening
        holder.Kategori.text=currentitem.kategori
        holder.SubKategori.text=currentitem.subkategori
        holder.HargaSubKategori.text=currentitem.hargaSubKategori
        holder.Jumlah.text=currentitem.jumlah
        holder.SubTotal.text=currentitem.subtotal
    }

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val Tanggalhistori : TextView = itemView.findViewById(R.id.Tanggalhistori)
        val NamaBank : TextView = itemView.findViewById(R.id.NamaBank)
        val NamaNasabah : TextView = itemView.findViewById(R.id.NamaNasabah)
        val NamaPetugas : TextView = itemView.findViewById(R.id.NamaPetugas)
        val MetodPembayaran : TextView = itemView.findViewById(R.id.MetodPembayaran)
        val Rekening : TextView = itemView.findViewById(R.id.Rekening)
        val Kategori : TextView = itemView.findViewById(R.id.Kategori)
        val SubKategori : TextView = itemView.findViewById(R.id.SubKategori)
        val HargaSubKategori : TextView = itemView.findViewById(R.id.HargaSubKategori)
        val Jumlah : TextView = itemView.findViewById(R.id.Jumlah)
        val SubTotal : TextView = itemView.findViewById(R.id.SubTotal)

    }
}