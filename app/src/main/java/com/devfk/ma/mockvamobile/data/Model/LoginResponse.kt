package com.devfk.ma.mockvamobile.data.Model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val id: String,
    val accountId: String,
    val lastLoginTimestamp: String,
    val sessionStatus: String
)