package com.devfk.ma.mockvamobile.data.Interface

import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.data.Model.TransResponse

interface ITrans {
    fun onTransResponse(transResponse: TransResponse)
    fun onFail(throwable: ANError)
}