package com.devfk.ma.mockvamobile.data.Presenter

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.devfk.ma.mockvamobile.data.Interface.IHome
import com.devfk.ma.mockvamobile.data.Model.AccountDetail
import com.devfk.ma.mockvamobile.data.Model.LoginResponse
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.ui.Fragment.HomeFragment
import okhttp3.Response
import org.json.JSONObject

class HomePresenter(context: HomeFragment){
    val HomeView = context as IHome

    fun get(accountId: String, sessionId: String) {

        AndroidNetworking.get(Constant.BASE_URL+"rest/account/detail")
            .addHeaders("Content-Type","application/json")
            .addHeaders("_sessionId",sessionId)
            .addQueryParameter("id",accountId)
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndJSONObject(object : OkHttpResponseAndJSONObjectRequestListener {
                override fun onResponse(okHttpResponse: Response?, response: JSONObject?) {
                    var account = AccountDetail(
                        response?.get("id").toString(),
                        response?.get("name").toString(),
                        response?.get("username").toString(),
                        response?.get("balance").toString()
                    )

                    HomeView.onGetAccount(account)
                }

                override fun onError(error: ANError) { // handle error
                    HomeView.onFail(error)
                }
            })
//            .getAsObject(AccountDetail::class.java, object :
//                ParsedRequestListener<AccountDetail> {
//                override fun onResponse(response: AccountDetail) {
//                    HomeView.onGetAccount(response)
//                }
//                override fun onError(error: ANError) { // handle error
//                    HomeView.onFail(error)
//                }
//            })
    }
}