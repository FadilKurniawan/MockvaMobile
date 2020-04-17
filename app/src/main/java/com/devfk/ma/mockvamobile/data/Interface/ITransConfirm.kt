package com.devfk.ma.mockvamobile.data.Interface

import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.data.Model.TransConfirmResponse
import com.devfk.ma.mockvamobile.data.Model.TransResponse

interface ITransConfirm {
    fun onTransConfirmResponse(transResponse: TransConfirmResponse)
    fun onFail(throwable: ANError)
}