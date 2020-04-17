package com.devfk.ma.mockvamobile.data.Presenter

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.OkHttpResponseListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.devfk.ma.mockvamobile.data.Interface.IAccount
import com.devfk.ma.mockvamobile.data.Interface.IHome
import com.devfk.ma.mockvamobile.data.Model.AccountDetail
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.ui.Fragment.AccountFragment
import com.devfk.ma.mockvamobile.ui.Fragment.HomeFragment
import okhttp3.Response
import org.json.JSONObject

class AccountPresenter(context: AccountFragment){
    val AccountView = context as IAccount

    fun del(sessionId: String) {
        AndroidNetworking.delete(Constant.BASE_URL+"rest/auth/logout")
            .addHeaders("_sessionId",sessionId)
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponse(object : OkHttpResponseListener{
                override fun onResponse(response: Response?) {
                    AccountView.onLogout()
                }

                override fun onError(error: ANError?) {
                    AccountView.onFail(error!!)
                }

            })
    }
}