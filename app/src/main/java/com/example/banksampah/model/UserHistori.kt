package com.example.banksampah.model

data class UserHistori (
    var hargaSubKategori: Long ?= 0,
    var jumlah: Double?=null,
    var kategori:String?=null,
    var namaBank:String?=null,
    var namaNasabah:String?=null,
    var namaPetugas:String?=null,
    var pembayaran:String?=null,
    var rekening:String?=null,
    var subkategori:String?=null,
    var subtotal: Long?=null,
    var tanggal:String?=null
//    var tanggalhistori:String?=null
)