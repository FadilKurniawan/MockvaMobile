package com.devfk.ma.mockvamobile.data.Interface

import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.data.Model.LoginResponse

interface ILogin {
    fun onLogin(LoginResponse: LoginResponse)
    fun onFail(throwable: ANError)
}