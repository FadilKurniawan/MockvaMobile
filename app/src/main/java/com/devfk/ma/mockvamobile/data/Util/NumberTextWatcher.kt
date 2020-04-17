package com.devfk.ma.mockvamobile.data.Util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.ParseException


class NumberTextWatcher(et: EditText) : TextWatcher {
    private var df: DecimalFormat? = null
    private var dfnd: DecimalFormat? = null
    private var hasFractionalPart = false

    private var et: EditText? = null


    init {
        df = DecimalFormat("#,###.##")
        df!!.isDecimalSeparatorAlwaysShown = true
        dfnd = DecimalFormat("#,###.##")
        this.et = et
        hasFractionalPart = false
    }

    private val TAG = "NumberTextWatcher"

    override fun afterTextChanged(s: Editable) {
        et!!.removeTextChangedListener(this)
        try {
            val inilen: Int = et!!.text.length
            val v: String = s.toString().replace(
                java.lang.String.valueOf(df?.getDecimalFormatSymbols()?.getGroupingSeparator()),
                ""
            )
            val n: Number? = df?.parse(v)
            val cp = et!!.selectionStart
            if (hasFractionalPart) {
                et?.setText(df?.format(n))
            } else {
                et?.setText(dfnd?.format(n))
            }
            val endlen: Int = et!!.text.length
            val sel = cp + (endlen - inilen)
            if (sel > 0 && sel <= et!!.text.length) {
                et!!.setSelection(sel)
            } else { // place cursor at the end?
                et!!.setSelection(et!!.text.length - 1)
            }
        } catch (nfe: NumberFormatException) { // do nothing?
        } catch (e: ParseException) { // do nothing?
        }
        et!!.addTextChangedListener(this)
    }

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        hasFractionalPart =
            s.toString().contains(java.lang.String.valueOf(df?.decimalFormatSymbols?.decimalSeparator))
    }

}