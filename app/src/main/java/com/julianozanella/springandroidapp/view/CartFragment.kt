package com.julianozanella.springandroidapp.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.domain.Cart
import com.julianozanella.springandroidapp.domain.CartItem
import com.julianozanella.springandroidapp.service.CartService
import com.julianozanella.springandroidapp.view.adapter.CartAdapter
import com.julianozanella.springandroidapp.view.util.IReplaceFragAndTitle
import com.julianozanella.springandroidapp.viewModel.CartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import java.text.NumberFormat


class CartFragment : Fragment() {

    private val numberFormat = NumberFormat.getCurrencyInstance()
    private lateinit var viewModel: CartViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!)[CartViewModel::class.java]
        viewModel.setService(CartService(activity!!))
        val adapter = CartAdapter()
        viewModel.getCart().observe(this, Observer<Cart> {
            if (it != null) {
                showHideEmptyChart(it.items.isEmpty())
                adapter.items = it.items
            }
        })
        viewModel.getTotal().observe(this, Observer {
            tv_total_value.text = numberFormat.format(it)
        })
        rv_cart.layoutManager = LinearLayoutManager(activity)
        rv_cart.adapter = adapter
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_cart.addItemDecoration(divider)
        bt_finalize.setOnClickListener { finalizeOrder() }
        bt_purchase_continue.setOnClickListener { activity?.onBackPressed() }
        adapter.clickListener = object : CartAdapter.CartClickListener {
            override fun onClickListener(position: Int, view: View?, cartItem: CartItem) {
                when (view?.id) {
                    R.id.iv_cart_remove -> viewModel.decreaseQuantity(cartItem.produto)
                    R.id.iv_cart_add -> viewModel.increaseQuantity(cartItem.produto)
                    R.id.iv_cart_delete -> viewModel.removeProduct(cartItem.produto)
                }
            }
        }
    }

    private fun finalizeOrder() {
        if (activity is IReplaceFragAndTitle) {
            (activity as IReplaceFragAndTitle).replaceFragment(PickAddressFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is IReplaceFragAndTitle) {
            (activity as IReplaceFragAndTitle)
                .updateToolbarTitleInFragment(R.string.cart)
            (activity as IReplaceFragAndTitle).hideFloatingButton(true)
        }
    }

    override fun onPause() {
        super.onPause()
        if (activity is IReplaceFragAndTitle) {
            (activity as IReplaceFragAndTitle).hideFloatingButton(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    private fun showHideEmptyChart(empty: Boolean) {
        tv_empty_chart.visibility = if (empty) View.VISIBLE else View.GONE
        rv_cart.visibility = if (empty) View.GONE else View.VISIBLE
        tv_total.visibility = if (empty) View.GONE else View.VISIBLE
        tv_total_value.visibility = if (empty) View.GONE else View.VISIBLE
        bt_finalize.visibility = if (empty) View.GONE else View.VISIBLE
    }
}
