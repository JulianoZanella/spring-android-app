package com.julianozanella.springandroidapp.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.domain.Produto
import com.julianozanella.springandroidapp.extensions.replaceFragment
import com.julianozanella.springandroidapp.extensions.setTitle
import com.julianozanella.springandroidapp.service.CartService
import com.julianozanella.springandroidapp.service.ImageService
import com.julianozanella.springandroidapp.view.util.IReplaceFragAndTitle
import kotlinx.android.synthetic.main.fragment_product_details.*
import java.text.NumberFormat


class ProductDetailsFragment : Fragment() {
    private var product: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(PRODUCT) as Produto?
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val numberFormat = NumberFormat.getCurrencyInstance()
        val imgService = ImageService()
        if (product != null) {
            product!!.let {
                tv_product_detail_name.text = it.nome
                tv_product_detail_value.text = numberFormat.format(it.preco)
                imgService.setProductLargeImage(iv_product_detail, it.id)
            }
            bt_add_to_chart.setOnClickListener { addToChart(product!!) }
        } else {
            Toast.makeText(activity, getString(R.string.msg_product_detial_error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        setTitle(product?.nome ?: getString(R.string.details))
    }

    private fun addToChart(obj: Produto) {
        val service = CartService(activity!!)
        service.addProduct(obj)
        replaceFragment(CartFragment())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }


    companion object {

        private const val PRODUCT = "product"

        @JvmStatic
        fun newInstance(obj: Produto) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PRODUCT, obj)
                }
            }
    }
}
