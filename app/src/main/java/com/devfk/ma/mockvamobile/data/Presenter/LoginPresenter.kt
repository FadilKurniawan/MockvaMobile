package com.devfk.ma.mockvamobile.data.Presenter

import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.gsonparserfactory.GsonParserFactory
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.androidnetworking.interfaces.OkHttpResponseListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.devfk.ma.mockvamobile.data.Interface.ILogin
import com.devfk.ma.mockvamobile.data.Model.LoginResponse
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.google.gson.Gson
import okhttp3.Response
import org.json.JSONObject

class LoginPresenter (context: Context){
    val LoginView = context as ILogin

    fun post(user: String, pass: String) {

        var jsonObject:JSONObject = JSONObject()
        jsonObject.put("username",user)
        jsonObject.put("password",pass)

        AndroidNetworking.post(Constant.BASE_URL+"rest/auth/login")
            .addHeaders("Content-Type","application/json")
            .addJSONObjectBody(jsonObject)
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndJSONObject(object : OkHttpResponseAndJSONObjectRequestListener {
                override fun onResponse(okHttpResponse: Response?, response: JSONObject?) {

                    println("*** on response : ${response}")
                    var loginResp:LoginResponse  = LoginResponse(
                        response?.get("id").toString(),
                        response?.get("accountId").toString(),
                        response?.get("lastLoginTimestamp").toString(),
                        response?.get("sessionStatus").toString()
                    )
                    LoginView.onLogin(loginResp)
                }

                override fun onError(error: ANError) { // handle error
                    LoginView.onFail(error)
                }
            })
//            .getAsObject(LoginResponse::class.java, object :
//                ParsedRequestListener<LoginResponse> {
//                override fun onResponse(response: LoginResponse) {
//                    println("*** on response : ${response.id}")
//                    LoginView.onLogin(response)
//                }
//                override fun onError(error: ANError) { // handle error
//                    LoginView.onFail(error)
//                }
//            })

    }
}