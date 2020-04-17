package com.devfk.ma.mockvamobile.ui.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Interface.ILogin
import com.devfk.ma.mockvamobile.data.Model.LoginResponse
import com.devfk.ma.mockvamobile.data.Presenter.LoginPresenter
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.data.Util.UtilApp
import com.devfk.ma.mockvamobile.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ILogin, View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialization()
    }

    private fun initialization() {
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnLogin->{
                if (UtilApp(this).isNetworkAvailable()) {
                    loadview.visibility = Constant.VISIBLE
                    btnLogin.visibility = View.GONE
                    LoginPresenter(this).post(
                        etxtUsername.text.toString(),
                        etxtPassword.text.toString()
                    )
                }else{
                    var alertDialog:CustomAlertDialog = CustomAlertDialog(this)
                    alertDialog.setTitleandContent("Error", Constant.No_Network, "OK", Constant.Rc_Session)
                    alertDialog.setTitle(Constant.HIDE)
                    alertDialog.show()
                }
            }
        }
    }

    override fun onLogin(loginresp: LoginResponse) {
        loadview.visibility = View.GONE
        btnLogin.visibility = View.VISIBLE
        var sp:SharedPreferences = getSharedPreferences(Constant.PREF_NAME,Constant.PRIVATE_MODE)
        var save = sp.edit()
        save.putString("sessionId",loginresp.id)
        save.putString("accountId",loginresp.accountId)
        save.apply()

        println("*** login session: ${loginresp.id}")
        val intent = Intent(this,BaseActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onFail(error: ANError) {
        loadview.visibility = View.GONE
        btnLogin.visibility = View.VISIBLE
//        Toast.makeText(this,"error code: ${error.errorBody}",Toast.LENGTH_LONG).show()
        var alertDialog:CustomAlertDialog = CustomAlertDialog(this)
        alertDialog.setTitleandContent("Error", error.errorBody, "OK", error.response.header("Rc"))
        alertDialog.setTitle(Constant.HIDE)
        alertDialog.show()
    }


}
