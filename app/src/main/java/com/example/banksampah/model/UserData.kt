package com.example.banksampah.model

data class UserData (
    var userName:String,
    var isDeleted: Boolean = false,
    var isModified: Boolean = false,
    var modifiedText: String = ""
    )
