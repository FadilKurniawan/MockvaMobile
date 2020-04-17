package com.devfk.ma.mockvamobile.ui.Fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Interface.IHome
import com.devfk.ma.mockvamobile.data.Model.AccountDetail
import com.devfk.ma.mockvamobile.data.Presenter.HomePresenter
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.data.Util.UtilApp
import com.devfk.ma.mockvamobile.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import java.text.NumberFormat
import java.util.*


class HomeFragment : Fragment(), IHome{

    lateinit var edt_accNumber: EditText
    lateinit var edt_Name: EditText
    lateinit var edt_Balance: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        var btn_back = view.findViewById<Button>(R.id.btn_back)
        var header = view.findViewById<TextView>(R.id.AppbarHeader)
        var loadview = view.findViewById<View>(R.id.loadview)

        edt_accNumber = view.findViewById<EditText>(R.id.edt_AccountNumber)
        edt_Name = view.findViewById<EditText>(R.id.edt_Name)
        edt_Balance = view.findViewById<EditText>(R.id.edt_Balance)

        header.gravity = Gravity.CENTER
        btn_back.visibility = View.GONE
        loadview.visibility = View.VISIBLE

        requesting()

        return view
    }

    private fun requesting() {
        if (UtilApp(context!!).isNetworkAvailable()) {
            var sp: SharedPreferences = context!!.getSharedPreferences(Constant.PREF_NAME, Constant.PRIVATE_MODE)
            println("*** Home session: ${sp.getString("sessionId", "").toString()}")
            HomePresenter(this)
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
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onGetAccount(account: AccountDetail) {
        loadview.visibility = View.GONE
        edt_accNumber.setText(account.id)
        edt_Name.setText(account.name)
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        edt_Balance.setText(formatRupiah.format(account.balance.toDouble()).toString())
    }

    override fun onFail(error: ANError) {
        loadview.visibility = View.GONE
//        Toast.makeText(context,"error code: ${error.errorCode}", Toast.LENGTH_LONG).show()
//        Toast.makeText(this,"error code: ${error.errorBody}",Toast.LENGTH_LONG).show()
        var alertDialog: CustomAlertDialog = CustomAlertDialog(context!!)
        alertDialog.setTitleandContent("Error",error.errorBody,"OK",error.response.header("Rc"))
        alertDialog.setTitle(Constant.HIDE)
        alertDialog.show()
    }
}
