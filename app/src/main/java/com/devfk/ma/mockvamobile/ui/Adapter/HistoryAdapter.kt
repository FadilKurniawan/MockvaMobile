package com.devfk.ma.mockvamobile.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.devfk.ma.mockvamobile.R
import com.devfk.ma.mockvamobile.data.Model.HistoryItem

class HistoryAdapter(nameItem: List<HistoryItem>) : BaseAdapter(){
    private val item = nameItem

    @SuppressLint("ViewHolder")
    override fun getView(position:Int, convertView: View?, parent: ViewGroup?): View {
        // Inflate the custom view
        val inflater = parent?.context?.
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view    = inflater.inflate(R.layout.list_item,null)
        // Get the custom view widgets reference
        val date = view.findViewById<TextView>(R.id.txvDateHistory)
        val amount = view.findViewById<TextView>(R.id.txvAmountHistory)
        val ref = view.findViewById<TextView>(R.id.txvRefHistory)
        val dest = view.findViewById<TextView>(R.id.txvDestHistory)

        date.text = item[position].transactionTimestamp
        amount.text = item[position].amount.toString()
        ref.text = item[position].clientRef
        dest.text = item[position].accountDstId

        return view
    }

    override fun getItem(position: Int): Any? {
        return item[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Count the items
    override fun getCount(): Int {
        return item.size
    }

}