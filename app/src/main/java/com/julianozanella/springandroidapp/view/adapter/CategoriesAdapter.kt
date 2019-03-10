package com.julianozanella.springandroidapp.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.domain.Categoria
import com.julianozanella.springandroidapp.service.ImageService


class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var items: List<Categoria> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.category_item,
                parent,
                false
            )
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        private val tvLabel: TextView = itemView.findViewById(R.id.tv_label)
        private val service = ImageService()

        fun setData(categoria: Categoria) {
            tvLabel.text = categoria.nome
            service.setCategoryImage(ivIcon, categoria.id)
        }
    }

}