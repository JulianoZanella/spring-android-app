package com.julianozanella.springandroidapp.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.domain.CartItem
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.dto.EnderecoDTO
import com.julianozanella.springandroidapp.dto.PedidoDTO
import com.julianozanella.springandroidapp.extensions.hideFloatingButton
import com.julianozanella.springandroidapp.extensions.setTitle
import com.julianozanella.springandroidapp.service.CartService
import com.julianozanella.springandroidapp.service.ImageService
import com.julianozanella.springandroidapp.viewModel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order_confirmation.*
import kotlinx.android.synthetic.main.order_item.view.*
import java.text.NumberFormat

class OrderConfirmationFragment : Fragment() {

    private val numFormat = NumberFormat.getCurrencyInstance()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(activity!!)[OrderViewModel::class.java]
        val pedidoDTO = viewModel.pedidoDTO.value
        viewModel.findById(pedidoDTO?.cliente!!.id).observe(this, Observer {
            if (it != null) fillViews(pedidoDTO, it)
        })
    }

    private fun fillViews(pedidoDTO: PedidoDTO, clienteDTO: ClienteDTO) {
        clienteDTO.let {
            tv_order_client_name.text = it.nome
            tv_order_client_email.text = it.email
        }
        val enderecoDTO: EnderecoDTO? = findAddress(pedidoDTO.enderecoDeEntrega?.id, clienteDTO.enderecos)
        enderecoDTO?.let {
            tv_order_street.text = it.logradouro
            tv_order_number.text = it.numero
            tv_order_complement.text = it.complemento
            tv_order_neighborhood.text = it.bairro
            tv_order_cep.text = it.cep
            tv_order_city.text = it.cidade?.nome
            tv_order_state.text = it.cidade?.estado?.nome
        }
        if (pedidoDTO.pagamento?.type == "pagamentoComCartao") {
            tv_order_payment_portions.text = getString(R.string.order_portions, pedidoDTO.pagamento?.numeroDeParcelas)
        } else {
            tv_order_payment_type.text = getString(R.string.ticket_payment)
            tv_order_payment_portions.visibility = View.GONE
        }
        val cartService = CartService(activity!!)
        val cart = cartService.getCart()
        cart.items.forEach {
            container_order_items.addView(inflateItem(it))
        }
        tv_order_total.text = numFormat.format(cartService.getTotal())
        bt_order_confirm.setOnClickListener { confirmOrder(pedidoDTO) }
        bt_order_back.setOnClickListener { activity?.onBackPressed() }

    }

    private fun confirmOrder(pedidoDTO: PedidoDTO) {
        Log.d("Confirm", pedidoDTO.toString())
    }

    private fun findAddress(id: String?, list: Array<EnderecoDTO>?): EnderecoDTO? {
        id ?: return null
        list ?: return null
        return list.find { x -> x.id == id }
    }

    private fun inflateItem(item: CartItem): View {
        val inflater: LayoutInflater = layoutInflater
        val view = inflater.inflate(R.layout.order_item, container_order_items, false)
        ImageService().setProductImage(view.iv_order_item, item.produto.id)
        view.tv_order_item_name.text = item.produto.nome
        view.tv_order_item_value.text = numFormat.format(item.produto.preco)
        view.tv_order_item_quantity.text = item.quantidade.toString()
        val total = item.quantidade * item.produto.preco
        view.tv_order_item_total.text = numFormat.format(total)
        return view
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.order_confirm_tittle)
        hideFloatingButton(true)
    }

    override fun onPause() {
        super.onPause()
        hideFloatingButton(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_confirmation, container, false)
    }
}
