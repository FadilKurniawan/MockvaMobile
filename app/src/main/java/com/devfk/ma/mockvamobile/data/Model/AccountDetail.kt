package com.devfk.ma.mockvamobile.data.Model


import com.google.gson.annotations.SerializedName

data class AccountDetail(
    val id: String,
    val name: String,
    val username: String,
    val balance: String
)