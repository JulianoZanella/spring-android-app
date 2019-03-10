package com.julianozanella.springandroidapp.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.domain.Produto
import com.julianozanella.springandroidapp.service.ImageService


class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    var clickListener: CategoryClickListener? = null

    var items: List<Produto> = arrayListOf()
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        private val tvLabel: TextView = itemView.findViewById(R.id.tv_label)
        private val service = ImageService()

        fun setData(produto: Produto) {
            tvLabel.text = produto.nome
            service.setProductImage(ivIcon, produto.id)
        }

        override fun onClick(v: View?) {
            clickListener?.onClickListener(adapterPosition, v, items[adapterPosition])
        }
    }

    interface CategoryClickListener {
        fun onClickListener(posicao: Int, view: View?, produto: Produto)
    }
}