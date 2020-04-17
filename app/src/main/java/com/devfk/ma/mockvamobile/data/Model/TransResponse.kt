package com.devfk.ma.mockvamobile.data.Model


import com.google.gson.annotations.SerializedName

data class TransResponse(
    val inquiryId: String,
    val accountSrcId: String,
    val accountDstId: String,
    val accountSrcName: String,
    val accountDstName: String,
    val amount: Double
)