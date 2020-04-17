package com.devfk.ma.mockvamobile.ui.Component

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.ui.Activity.LoginActivity


class CustomAlertDialog(var context: Context) {
    interface AlertDialogListner {
        fun onDismissClick()
    }

    private val dialog: Dialog
    protected var confirmBtn: TextView? = null
    protected var txvContent: TextView? = null
    protected var txvTitle: TextView? = null
    protected var title: String? = null
    protected var content: String? = null
    protected var btns: String? = null
    protected var header: String? = null

    fun setTitleandContent(
        title: String?,
        content: String?,
        btns: String?,
        header: String?
    ) {
        this.title = title
        this.content = content
        this.btns = btns
        this.header = header
        updateUi()
    }

    private fun updateUi() {
        txvTitle!!.text = title
        txvContent!!.text = content
        confirmBtn!!.text = btns
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    val isShowing: Boolean
        get() = dialog.isShowing

    private fun initViews() {
        txvTitle = dialog.findViewById<View>(R.id.txvTitle) as TextView
        confirmBtn = dialog.findViewById<View>(R.id.btnConfirm) as TextView
        txvContent =
            dialog.findViewById<View>(R.id.txvContent) as TextView
    }

    companion object {
        private val listner: AlertDialogListner? = null
    }

    init {
        dialog = Dialog(context)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_alert_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (header!!.toLowerCase() == Constant.Rc_Session.toLowerCase()){
                    var intent = Intent(context,LoginActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                }else{
                    listner?.onDismissClick()
                }
            }
            false
        }
        dialog.show()
        initViews()
        confirmBtn!!.setOnClickListener {
            if (header!!.toLowerCase() == Constant.Rc_Session.toLowerCase()){
                var intent = Intent(context,LoginActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }else{
                dismiss()
            }
        }
    }

    fun setTitle(code:Int){
        txvTitle?.visibility = code
    }

}