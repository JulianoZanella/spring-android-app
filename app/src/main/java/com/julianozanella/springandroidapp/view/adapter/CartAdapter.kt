package com.julianozanella.springandroidapp.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.domain.CartItem
import com.julianozanella.springandroidapp.service.ImageService
import java.text.NumberFormat


class CartAdapter() : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var clickListener: CartClickListener? = null
    var items: List<CartItem> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.cart_item,
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

        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_chart_icon)
        private val ivRemove: ImageView = itemView.findViewById(R.id.iv_chart_remove)
        private val ivAdd: ImageView = itemView.findViewById(R.id.iv_chart_remove)
        private val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete)
        private val tvName: TextView = itemView.findViewById(R.id.tv_product_name)
        private val tvValue: TextView = itemView.findViewById(R.id.tv_product_chart_value)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tv_chart_count)
        private val service = ImageService()
        private val numberFormat = NumberFormat.getCurrencyInstance()

        fun setData(obj: CartItem) {
            tvName.text = obj.produto.nome
            service.setProductImage(ivIcon, obj.produto.id)
            tvValue.text = numberFormat.format(obj.produto.preco)
            tvQuantity.text = obj.quantidade.toString()
        }

        override fun onClick(v: View?) {
            clickListener?.onClickListener(adapterPosition, v, items[adapterPosition])
        }
    }

    interface CartClickListener {
        fun onClickListener(position: Int, view: View?, cartItem: CartItem)
    }
}