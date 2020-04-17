package com.devfk.ma.mockvamobile.ui.Fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.androidnetworking.error.ANError

import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Interface.IAccount
import com.devfk.ma.mockvamobile.data.Presenter.AccountPresenter
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.data.Util.UtilApp
import com.devfk.ma.mockvamobile.ui.Activity.LoginActivity
import com.devfk.ma.mockvamobile.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_login.*


class AccountFragment : Fragment() ,IAccount,View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_account, container, false)
        var btn_back = view.findViewById<Button>(R.id.btn_back)
        var header = view.findViewById<TextView>(R.id.AppbarHeader)
        var btn_logout = view.findViewById<Button>(R.id.btn_logOut)
        var loadview = view.findViewById<View>(R.id.loadview)

        loadview.visibility = View.GONE
        btn_logout.setOnClickListener(this)
        btn_back.visibility = View.INVISIBLE
        header.text = resources.getString(R.string.header_account)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onLogout() {
        loadview.visibility = View.GONE
        val sp: SharedPreferences = context!!.getSharedPreferences(Constant.PREF_NAME, Constant.PRIVATE_MODE)
        var editor = sp.edit()
        editor.clear()
        editor.apply()
        var intent = Intent(context,LoginActivity::class.java)
        startActivity(intent)
        activity!!.finish()
    }

    override fun onFail(error: ANError) {
        loadview.visibility = View.GONE
//        Toast.makeText(context,"error code: ${error.errorCode}",Toast.LENGTH_LONG).show()
        var alertDialog: CustomAlertDialog = CustomAlertDialog(context!!)
        alertDialog.setTitleandContent("Error", error.errorBody, "OK", error.response.header("Rc"))
        alertDialog.setTitle(Constant.HIDE)
        alertDialog.show()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_logOut->{
                requesting()
            }
        }
    }

    private fun requesting() {
        val sp: SharedPreferences = context!!.getSharedPreferences(Constant.PREF_NAME, Constant.PRIVATE_MODE)

        if (UtilApp(context!!).isNetworkAvailable()) {
            AccountPresenter(this)
                .del(sp.getString("sessionId", "").toString())
        }else{
            var alertDialog:CustomAlertDialog = CustomAlertDialog(context!!)
            alertDialog.setTitleandContent("Error", Constant.No_Network, "OK", Constant.Rc_Session)
            alertDialog.setTitle(Constant.HIDE)
            alertDialog.show()
        }
    }
}
