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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Interface.ITransConfirm
import com.devfk.ma.mockvamobile.data.Model.TransConfirmResponse
import com.devfk.ma.mockvamobile.data.Presenter.TransferConfirmPresenter
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.data.Util.NumberTextWatcher
import com.devfk.ma.mockvamobile.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_login.*




class TransResultFragment(array: java.util.ArrayList<String>) : Fragment(){

    private var dataTrans: java.util.ArrayList<String>? = ArrayList()


    lateinit var txv_accSrc:TextView
    lateinit var txv_accSrcName:TextView
    lateinit var txv_accDest:TextView
    lateinit var txv_accDestName:TextView
    lateinit var txv_amount:TextView
    lateinit var txv_refnum:TextView
    lateinit var txv_timestamp:TextView

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
    ): View? {        var view = inflater.inflate(R.layout.fragment_trans_result, container, false)
        var btn_back = view.findViewById<Button>(R.id.btn_back)
        var header = view.findViewById<TextView>(R.id.AppbarHeader)

        txv_accSrc = view.findViewById(R.id.txv_accSrc)
        txv_accSrcName = view.findViewById(R.id.txv_accSrcName)
        txv_accDest = view.findViewById(R.id.txv_accDest)
        txv_accDestName = view.findViewById(R.id.txv_accDestName)
        txv_amount = view.findViewById<EditText>(R.id.txv_amount)
        txv_refnum = view.findViewById<EditText>(R.id.txv_RefNum)
        txv_timestamp = view.findViewById<EditText>(R.id.txv_TimeStamp)

        initialization()

        btn_back.visibility = View.INVISIBLE
        header.text = resources.getString(R.string.header_transfer)

        return view
    }

    private fun initialization() {
        txv_accSrc.setText(dataTrans?.get(Constant.accSrc))
        txv_accSrcName.setText(dataTrans?.get(Constant.accSrcName))
        txv_accDest.setText(dataTrans?.get(Constant.accDest))
        txv_accDestName.setText(dataTrans?.get(Constant.accDestName))
        txv_amount.setText(dataTrans?.get(Constant.amount))
        txv_refnum.setText(dataTrans?.get(Constant.refnum))
        txv_timestamp.setText(dataTrans?.get(Constant.timestamp))
    }

}
