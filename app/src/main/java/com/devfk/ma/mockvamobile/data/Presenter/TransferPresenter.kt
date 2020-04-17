package com.devfk.ma.mockvamobile.data.Presenter

import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.devfk.ma.mockvamobile.data.Interface.ITrans
import com.devfk.ma.mockvamobile.data.Model.TransResponse
import com.devfk.ma.mockvamobile.data.Util.Constant
import okhttp3.Response
import org.json.JSONObject

class TransferPresenter (context: Fragment){
    val TransView = context as ITrans

    fun post(accSrc: String, accDst: String, amount:Double, sessionId:String) {

        var jsonObject:JSONObject = JSONObject()
        jsonObject.put("accountSrcId", accSrc)
        jsonObject.put("accountDstId", accDst)
        jsonObject.put("amount", amount)

        AndroidNetworking.post(Constant.BASE_URL+"rest/account/transaction/transferInquiry")
            .addHeaders("Content-Type","application/json")
            .addHeaders("_sessionId",sessionId)
            .addJSONObjectBody(jsonObject)
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndJSONObject(object :
                OkHttpResponseAndJSONObjectRequestListener {
                override fun onResponse(okHttpResponse: Response?, response: JSONObject?) {
                    var transfer = TransResponse(
                        response?.get("inquiryId").toString(),
                        response?.get("accountSrcId").toString(),
                        response?.get("accountDstId").toString(),
                        response?.get("accountSrcName").toString(),
                        response?.get("accountDstName").toString(),
                        response?.get("amount").toString().toDouble()
                    )

                    TransView.onTransResponse(transfer)
                }

                override fun onError(error: ANError) { // handle error
                    TransView.onFail(error)
                }
            })
//            .getAsObject(TransResponse::class.java, object :
//                ParsedRequestListener<TransResponse> {
//                override fun onResponse(response: TransResponse) {
//                    TransView.onTransResponse(response)
//                }
//                override fun onError(error: ANError) { // handle error
//                    TransView.onFail(error)
//                }
//            })
    }
}