package com.devfk.ma.mockvamobile.data.Interface

import com.androidnetworking.error.ANError

interface IAccount {
    fun onLogout()
    fun onFail(throwable: ANError)
}