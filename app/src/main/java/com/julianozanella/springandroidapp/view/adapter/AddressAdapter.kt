package com.julianozanella.springandroidapp.view.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.EnderecoDTO


class AddressAdapter : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    var clickListener: AddressClickListener? = null

    var items: List<EnderecoDTO> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.address_item,
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

        private val tvStreet: TextView = itemView.findViewById(R.id.tv_street)
        private val tvNumber: TextView = itemView.findViewById(R.id.tv_number)
        private val tvComplement: TextView = itemView.findViewById(R.id.tv_complement)
        private val tvNeighborhood: TextView = itemView.findViewById(R.id.tv_neighborhood)
        private val tvCep: TextView = itemView.findViewById(R.id.tv_cep)
        private val tvCity: TextView = itemView.findViewById(R.id.tv_city)
        private val tvState: TextView = itemView.findViewById(R.id.tv_state)

        fun setData(enderecoDTO: EnderecoDTO) {
            tvStreet.text = enderecoDTO.logradouro
            tvNumber.text = enderecoDTO.numero
            tvComplement.text = enderecoDTO.complemento
            tvNeighborhood.text = enderecoDTO.bairro
            tvCep.text = enderecoDTO.cep
            tvCity.text = enderecoDTO.cidade?.nome
            tvState.text = enderecoDTO.cidade?.estado?.nome
        }

        override fun onClick(v: View?) {
            clickListener?.onClickListener(adapterPosition, v, items[adapterPosition])
        }
    }

    interface AddressClickListener {
        fun onClickListener(position: Int, view: View?, enderecoDTO: EnderecoDTO)
    }
}