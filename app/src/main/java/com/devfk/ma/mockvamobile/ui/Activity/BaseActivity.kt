package com.devfk.ma.mockvamobile.ui.Activity

import android.app.PendingIntent.getActivity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Util.Constant
import com.devfk.ma.mockvamobile.ui.Fragment.AccountFragment
import com.devfk.ma.mockvamobile.ui.Fragment.HistoryFragment
import com.devfk.ma.mockvamobile.ui.Fragment.HomeFragment
import com.devfk.ma.mockvamobile.ui.Fragment.TransFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_base.*


class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        btmNav.setOnNavigationItemSelectedListener(this)
        val sp: SharedPreferences = getSharedPreferences(Constant.PREF_NAME, Constant.PRIVATE_MODE)
        openFragment(HomeFragment.newInstance())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home->{
                openFragment(HomeFragment.newInstance())
                return true
            }
            R.id.nav_trans->{
                openFragment(TransFragment.newInstance())
                return true
            }
            R.id.nav_history->{
                openFragment(HistoryFragment.newInstance())
                return true
            }
            R.id.nav_account->{
                openFragment(AccountFragment.newInstance())
                return true
            }
        }
        return false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager

        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
        super.onBackPressed()
    }
}
