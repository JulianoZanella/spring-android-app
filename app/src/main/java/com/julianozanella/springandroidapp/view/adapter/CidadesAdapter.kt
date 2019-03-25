package com.julianozanella.springandroidapp.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.julianozanella.springandroidapp.dto.CidadeDTO

class CidadesAdapter(private val context: Context) : BaseAdapter() {

    var items: List<CidadeDTO> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = TextView(context)
        textView.text = items[position].nome
        textView.textSize = 20F
        return textView
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}