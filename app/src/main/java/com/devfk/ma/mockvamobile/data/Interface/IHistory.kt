package com.devfk.ma.mockvamobile.data.Interface

import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.data.Model.HistoryItem

interface IHistory {
    fun onHistoryList(historyItem: ArrayList<HistoryItem>)
    fun onFail(throwable: ANError)
}