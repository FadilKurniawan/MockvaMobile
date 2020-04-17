package com.devfk.ma.mockvamobile.data.Presenter

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.devfk.ma.mockvamobile.data.Interface.IHistory
import com.devfk.ma.mockvamobile.data.Model.HistoryItem
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.ui.Fragment.HistoryFragment
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject

class HistoryPresenter(context: HistoryFragment){
    val HistoryView = context as IHistory

    fun get(accountId: String, sessionId: String) {
        AndroidNetworking.get(Constant.BASE_URL+"rest/account/transaction/log")
            .addHeaders("Content-Type","application/json")
            .addHeaders("_sessionId",sessionId)
            .addQueryParameter("id",accountId)
            .setPriority(Priority.HIGH)
            .build()
            .getAsOkHttpResponseAndJSONObject(object : OkHttpResponseAndJSONObjectRequestListener {
                override fun onResponse(okHttpResponse: Response?, response: JSONObject?) {
                    var array:ArrayList<HistoryItem> = ArrayList()
                    var item = response?.getJSONArray("data")
                    for (i in 0 until (item!!.length())) {
                        array.add(
                            HistoryItem(
                            item.getJSONObject(i).getString("id") ,
                            item.getJSONObject(i).getString("accountSrcId"),
                            item.getJSONObject(i).getString("accountDstId"),
                            item.getJSONObject(i).getString("clientRef"),
                            item.getJSONObject(i).getDouble("amount"),
                            item.getJSONObject(i).getString("transactionTimestamp")
                            )
                        )
                    }
                    HistoryView.onHistoryList(array)
                }

                override fun onError(error: ANError) { // handle error
                    HistoryView.onFail(error)
                }
            })
//            .getAsObject(HistoryResponse::class.java, object :
//                ParsedRequestListener<HistoryResponse> {
//                override fun onResponse(response: HistoryResponse) {
//                    HistoryView.onHistoryList(response.data)
//                }
//                override fun onError(error: ANError) { // handle error
//                    HistoryView.onFail(error)
//                }
//            })
    }
}