package com.devfk.ma.mockvamobile.ui.Fragment

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Interface.ITransConfirm
import com.devfk.ma.mockvamobile.data.Model.TransConfirmResponse
import com.devfk.ma.mockvamobile.data.Presenter.TransferConfirmPresenter
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.data.Util.NumberTextWatcher
import com.devfk.ma.mockvamobile.data.Util.UtilApp
import com.devfk.ma.mockvamobile.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_login.*


class TransConfirmFragment(array: java.util.ArrayList<String>) : Fragment(), View.OnClickListener, ITransConfirm{

    private var dataTrans: java.util.ArrayList<String>? = ArrayList()

    lateinit var edt_amount:EditText
    lateinit var edt_accSrc:EditText
    lateinit var edt_accSrcName:EditText
    lateinit var edt_accDest:EditText
    lateinit var edt_accDestName:EditText

    init {
        dataTrans?.addAll(array)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataTrans?.addAll(it.getStringArrayList(Constant.ARG_PARAM)!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {        var view = inflater.inflate(R.layout.fragment_trans_confirm, container, false)
        var btn_back = view.findViewById<Button>(R.id.btn_back)
        var header = view.findViewById<TextView>(R.id.AppbarHeader)
        var loadview = view.findViewById<View>(R.id.loadview)
        var btn_confirm = view.findViewById<Button>(R.id.btnConfirm)
        edt_amount = view.findViewById<EditText>(R.id.edt_Amount)
        edt_accSrc = view.findViewById(R.id.edt_AccountSrc)
        edt_accSrcName = view.findViewById(R.id.edt_AccountSrcName)
        edt_accDest = view.findViewById(R.id.edt_AccountDest)
        edt_accDestName = view.findViewById(R.id.edt_AccountDestName)

        initialization()
        btn_confirm.setOnClickListener(this)
        btn_back.setOnClickListener(this)

        btn_back.visibility = View.VISIBLE
        header.text = resources.getString(R.string.header_transfer)

        return view
    }

    private fun initialization() {
        edt_accSrc.setText(dataTrans?.get(Constant.accSrc))
        edt_accSrcName.setText(dataTrans?.get(Constant.accSrcName))
        edt_accDest.setText(dataTrans?.get(Constant.accDest))
        edt_accDestName.setText(dataTrans?.get(Constant.accDestName))
        edt_amount.addTextChangedListener(NumberTextWatcher(edt_amount))
        edt_amount.setText(dataTrans?.get(Constant.amount))
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnConfirm->{
                loadview.visibility = View.VISIBLE
                requesting()
            }
            R.id.btn_back->{
                val intent = Intent()
                intent.putExtra(Constant.FRAGMENT_KEY, dataTrans)
                targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, intent)
                fragmentManager!!.popBackStack()
            }
        }
    }
    private fun requesting() {
        val sp: SharedPreferences = context!!.getSharedPreferences(Constant.PREF_NAME, Constant.PRIVATE_MODE)
        if (UtilApp(context!!).isNetworkAvailable()) {
            TransferConfirmPresenter(this).post(
                dataTrans!![Constant.accSrc],
                dataTrans!![Constant.accDest],
                dataTrans!![Constant.amount].replace(",","").toDouble(),
                dataTrans!![Constant.inqueryId],
                sp.getString("sessionId","").toString()
            )
        }else{
            var alertDialog:CustomAlertDialog = CustomAlertDialog(context!!)
            alertDialog.setTitleandContent("Error", Constant.No_Network, "OK", Constant.Rc_Session)
            alertDialog.setTitle(Constant.HIDE)
            alertDialog.show()
        }
    }

    override fun onTransConfirmResponse(transResponse: TransConfirmResponse) {
        val intent = Intent()
        dataTrans!![Constant.amount] = edt_amount.text.toString()
        dataTrans!!.add(transResponse.clientRef)
        dataTrans!!.add(transResponse.transactionTimestamp.toString())

        intent.putExtra(Constant.FRAGMENT_KEY, dataTrans)
        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        fragmentManager!!.popBackStack()
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
