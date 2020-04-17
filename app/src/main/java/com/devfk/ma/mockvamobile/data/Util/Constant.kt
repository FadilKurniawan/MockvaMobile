package com.devfk.ma.mockvamobile.data.Util

import android.view.View

class Constant {
    companion object {
        const val BASE_URL =  "https://mockva.daksa.co.id/mockva-rest/"
        const val PRIVATE_MODE = 0
        const val PREF_NAME = "mockva-mobile"
        const val HIDE = View.GONE
        const val VISIBLE = View.VISIBLE

        const val ARG_PARAM = "transReponse"
        const val accSrc        = 0
        const val accSrcName    = 1
        const val accDest       = 2
        const val accDestName   = 3
        const val amount        = 4
        const val inqueryId     = 5
        const val refnum     = 6
        const val timestamp     = 7

        const val FRAGMENT_KEY: String = "TransConfirmResult"

        const val Rc_Session = "S1"

        const val No_Network = "No Internet Service"

        const val DATE_FORMAT ="yyyy-MM-dd HH:mm:ss"
    }
}