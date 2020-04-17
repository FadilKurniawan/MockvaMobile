package com.devfk.ma.mockvamobile.data.Model


import com.google.gson.annotations.SerializedName

data class HistoryItem(
    val id: String,
    val accountSrcId: String,
    val accountDstId: String,
    val clientRef: String,
    val amount: Double,
    val transactionTimestamp: String
)