package com.devfk.ma.mockvamobile.data.Interface

import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.data.Model.AccountDetail

interface IHome {
    fun onGetAccount(acoount: AccountDetail)
    fun onFail(throwable: ANError)
}