package com.devfk.ma.mockvamobile.data.Model


import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.*

data class TransConfirmResponse(
    val accountSrcId: String,
    val accountDstId: String,
    val amount: Double,
    val transactionTimestamp: String,
    val clientRef: String
)