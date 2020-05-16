package com.example.restrofinder.dashboard.views

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.restrofinder.R


class CustomSpinnerAdapter(
    val context: Context,
    val list: ArrayList<String>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowItem: String? = list[position]
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_item_spinner, null)
        val txtTitle = view.findViewById(R.id.tv_spinner) as TextView
        txtTitle.setText(rowItem)
        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }


}