package com.devfk.ma.mockvamobile.ui.Fragment

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.androidnetworking.error.ANError
import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Interface.ITrans
import com.devfk.ma.mockvamobile.data.Model.TransResponse
import com.devfk.ma.mockvamobile.data.Presenter.TransferPresenter
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.data.Util.NumberTextWatcher
import com.devfk.ma.mockvamobile.data.Util.UtilApp
import com.devfk.ma.mockvamobile.ui.Component.CustomAlertDialog
import kotlinx.android.synthetic.main.activity_login.*


class TransFragment : Fragment(), ITrans, View.OnClickListener{
    private val FRAGMENT_CODE: Int =1
    lateinit var layout: LinearLayout
    lateinit var edt_amount:EditText
    lateinit var edt_accDest:EditText
    lateinit var btn_trans:Button
    lateinit var frame:FrameLayout
    private var current = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_trans, container, false)
        var btn_back = view.findViewById<Button>(R.id.btn_back)
        var header = view.findViewById<TextView>(R.id.AppbarHeader)
        var loadview = view.findViewById<View>(R.id.loadview)
        btn_trans = view.findViewById<Button>(R.id.btnTransfer)
        edt_amount = view.findViewById<EditText>(R.id.edt_Amount)
        edt_accDest = view.findViewById(R.id.edt_AccountNumber)
        frame = view.findViewById<FrameLayout>(R.id.childContent)
        layout = view.findViewById(R.id.layout_trans)

        btn_trans.setOnClickListener(this)
        btn_back.visibility = View.INVISIBLE
        header.text = resources.getString(R.string.header_transfer)
        edt_amount.addTextChangedListener(NumberTextWatcher(edt_amount))

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            TransFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onTransResponse(transResponse: TransResponse) {
        loadview.visibility = View.GONE
        var array:java.util.ArrayList<String> = ArrayList()
        array.add(transResponse.accountSrcId)
        array.add(transResponse.accountSrcName)
        array.add(transResponse.accountDstId)
        array.add(transResponse.accountDstName)
        array.add(transResponse.amount.toString())
        array.add(transResponse.inquiryId)

        uiView()
        frame.visibility = View.VISIBLE

        val fragmentChild = TransConfirmFragment(array)
        fragmentChild.setTargetFragment(this, FRAGMENT_CODE)
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.childContent, fragmentChild)
        transaction.addToBackStack(null)
        transaction.commit()
//        childFragmentManager.beginTransaction().replace(R.id.childContent,TransConfirmFragment.newInstance(array)).commit()

    }

    private fun uiView() {
        btn_trans.isEnabled = !btn_trans.isEnabled
        edt_accDest.isEnabled = !edt_accDest.isEnabled
        edt_amount.isEnabled = !edt_amount.isEnabled
    }


    override fun onFail(error: ANError) {
        loadview.visibility = View.GONE
//        Toast.makeText(this,"error code: ${error.errorBody}",Toast.LENGTH_LONG).show()
        var alertDialog: CustomAlertDialog = CustomAlertDialog(context!!)
        alertDialog.setTitleandContent("Error", error.errorBody, "OK", error.response.header("Rc"))
        alertDialog.setTitle(Constant.HIDE)
        alertDialog.show()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnTransfer->{
                requesting()
                loadview.visibility = View.VISIBLE
            }
        }
    }
    private fun requesting() {
        val sp: SharedPreferences = context!!.getSharedPreferences(Constant.PREF_NAME, Constant.PRIVATE_MODE)
        if (UtilApp(context!!).isNetworkAvailable()) {
            TransferPresenter(this).post(
                sp.getString("accountId","").toString(),
                edt_accDest.text.toString(),
                edt_amount.text.toString().replace(",","").toDouble(),
                sp.getString("sessionId","").toString()
            )
        }else{
            var alertDialog:CustomAlertDialog = CustomAlertDialog(context!!)
            alertDialog.setTitleandContent("Error", Constant.No_Network, "OK", Constant.Rc_Session)
            alertDialog.setTitle(Constant.HIDE)
            alertDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === FRAGMENT_CODE && resultCode === Activity.RESULT_OK && data != null) {
            val value = data!!.getStringArrayListExtra(Constant.FRAGMENT_KEY)
            println("*** ${value.toString()}")
            val fragmentChild = TransResultFragment(value!!)
            val transaction: FragmentTransaction = childFragmentManager!!.beginTransaction()
            transaction.replace(R.id.childContent, fragmentChild)
            transaction.addToBackStack(null)
            transaction.commit()
        }else if(requestCode === FRAGMENT_CODE && resultCode === Activity.RESULT_CANCELED){
            uiView()
            frame.visibility = View.GONE
        }
    }
}
