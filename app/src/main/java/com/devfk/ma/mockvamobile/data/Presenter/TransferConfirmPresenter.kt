package com.devfk.ma.mockvamobile.data.Presenter

import android.annotation.SuppressLint
import android.os.Build
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.devfk.ma.mockvamobile.data.Interface.ITrans
import com.devfk.ma.mockvamobile.data.Interface.ITransConfirm
import com.devfk.ma.mockvamobile.data.Model.AccountDetail
import com.devfk.ma.mockvamobile.data.Model.TransConfirmResponse
import com.devfk.ma.mockvamobile.data.Model.TransResponse
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.ui.Fragment.TransFragment
import okhttp3.Response
import org.json.JSONObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class TransferConfirmPresenter (context: Fragment){
    val TransView = context as ITransConfirm

    fun post(accSrc: String, accDst: String, amount:Double, inquiryId:String,  sessionId:String) {

        var jsonObject:JSONObject = JSONObject()
        jsonObject.put("accountSrcId", accSrc)
        jsonObject.put("accountDstId", accDst)
        jsonObject.put("amount", amount)
        jsonObject.put("inquiryId", inquiryId)

        AndroidNetworking.post(Constant.BASE_URL+"rest/account/transaction/transfer")
            .addHeaders("Content-Type","application/json")
            .addHeaders("_sessionId",sessionId)
            .addJSONObjectBody(jsonObject)
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndJSONObject(object :
                OkHttpResponseAndJSONObjectRequestListener {
                @SuppressLint("SimpleDateFormat")
                override fun onResponse(okHttpResponse: Response?, response: JSONObject?) {
                    val unix =response?.get("transactionTimestamp").toString()

                    var account = TransConfirmResponse(
                        response?.get("accountSrcId").toString(),
                        response?.get("accountDstId").toString(),
                        response?.get("amount").toString().toDouble(),
                        unix,
                        response?.get("clientRef").toString()
                    )

                    TransView.onTransConfirmResponse(account)
                }

                override fun onError(error: ANError) { // handle error
                    TransView.onFail(error)
                }
            })
//            .getAsObject(TransConfirmResponse::class.java, object :
//                ParsedRequestListener<TransConfirmResponse> {
//                override fun onResponse(response: TransConfirmResponse) {
//                    TransView.onTransConfirmResponse(response)
//                }
//                override fun onError(error: ANError) { // handle error
//                    TransView.onFail(error)
//                }
//            })
    }
}