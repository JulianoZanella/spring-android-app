package com.julianozanella.springandroidapp.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.domain.Cart
import com.julianozanella.springandroidapp.dto.EnderecoDTO
import com.julianozanella.springandroidapp.dto.ItemPedidoDTO
import com.julianozanella.springandroidapp.dto.PedidoDTO
import com.julianozanella.springandroidapp.dto.RefDTO
import com.julianozanella.springandroidapp.extensions.KEY
import com.julianozanella.springandroidapp.extensions.getSharedPreference
import com.julianozanella.springandroidapp.view.adapter.AddressAdapter
import com.julianozanella.springandroidapp.view.util.IReplaceFragAndTitle
import com.julianozanella.springandroidapp.viewModel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_pick_address.*

class PickAddressFragment : Fragment() {

    private var pedidoDTO: PedidoDTO? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(activity!!)[OrderViewModel::class.java]
        val adapter = AddressAdapter()
        val email: String? = activity!!.getSharedPreference(KEY.EMAIL, String::class.java) as String?
        if (email != null) {
            viewModel.findByEmail(email).observe(this, Observer {
                if (it?.enderecos != null) {
                    adapter.items = it.enderecos!!.asList()
                    fillOrder(it.id)
                }
            })
        }
        rv_address.adapter = adapter
        rv_address.layoutManager = LinearLayoutManager(activity)
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_address.addItemDecoration(divider)
        adapter.clickListener = object : AddressAdapter.AddressClickListener {
            override fun onClickListener(position: Int, view: View?, enderecoDTO: EnderecoDTO) {
                nextPage(enderecoDTO.id)
            }
        }
    }

    private fun fillOrder(clientId: String) {
        val cart = activity!!.getSharedPreference(KEY.CART, Cart::class.java) as Cart
        val items: List<ItemPedidoDTO> =
            cart.items.map { x -> ItemPedidoDTO(x.quantidade, RefDTO(x.produto.id)) }
        pedidoDTO = PedidoDTO(RefDTO(clientId), null, null, items.toTypedArray())
    }

    private fun nextPage(id: String) {
        pedidoDTO?.enderecoDeEntrega = RefDTO(id)
        Log.d("Cart", pedidoDTO.toString())
        //TODO("Criar pagina de pagamento")
    }

    override fun onResume() {
        super.onResume()
        if (activity is IReplaceFragAndTitle) {
            (activity as IReplaceFragAndTitle)
                .updateToolbarTitleInFragment(R.string.pick_address_tittle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pick_address, container, false)
    }
}
