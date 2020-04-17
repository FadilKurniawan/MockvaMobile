package com.devfk.ma.mockvamobile.ui.Fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.androidnetworking.error.ANError

import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Interface.IHistory
import com.devfk.ma.mockvamobile.data.Model.HistoryItem
import com.devfk.ma.mockvamobile.data.Presenter.HistoryPresenter
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.data.Util.UtilApp
import com.devfk.ma.mockvamobile.ui.Adapter.HistoryAdapter
import com.devfk.ma.mockvamobile.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_login.loadview
import kotlinx.android.synthetic.main.fragment_history.*


class HistoryFragment : Fragment(),IHistory{

    lateinit var lv_history:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_history, container, false)
        var btn_back = view.findViewById<Button>(R.id.btn_back)
        var header = view.findViewById<TextView>(R.id.AppbarHeader)
        var loadview = view.findViewById<View>(R.id.loadview)
        lv_history =  view.findViewById<ListView>(R.id.lv_history)

        btn_back.visibility = View.INVISIBLE
        header.text = resources.getString(R.string.header_history)

        loadview.visibility = View.VISIBLE

        requesting()

        return view
    }

    private fun requesting() {
        val sp: SharedPreferences = context!!.getSharedPreferences(Constant.PREF_NAME, Constant.PRIVATE_MODE)
        if (UtilApp(context!!).isNetworkAvailable()) {
            HistoryPresenter(this)
                .get(
                    sp.getString("accountId", "").toString(),
                    sp.getString("sessionId", "").toString()
                )
        }else{
            var alertDialog:CustomAlertDialog = CustomAlertDialog(context!!)
            alertDialog.setTitleandContent("Error", Constant.No_Network, "OK", Constant.Rc_Session)
            alertDialog.setTitle(Constant.HIDE)
            alertDialog.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onHistoryList(historyItem: ArrayList<HistoryItem>) {
        loadview.visibility = View.GONE
        lv_history.adapter = HistoryAdapter(historyItem)
        if(historyItem.isEmpty()){
           txv_NoData.visibility = View.VISIBLE
        }else{
            txv_NoData.visibility = View.GONE
        }
    }

    override fun onFail(error: ANError) {
        loadview.visibility = View.GONE
//        Toast.makeText(this,"error code: ${error.errorBody}",Toast.LENGTH_LONG).show()
        var alertDialog: CustomAlertDialog = CustomAlertDialog(context!!)
        alertDialog.setTitleandContent("Error", error.errorBody, "OK", error.response.header("Rc"))
        alertDialog.setTitle(Constant.HIDE)
        alertDialog.show()
    }
}
