package com.julianozanella.springandroidapp.view


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.PagamentoDTO
import com.julianozanella.springandroidapp.dto.PedidoDTO
import com.julianozanella.springandroidapp.extensions.hideFloatingButton
import com.julianozanella.springandroidapp.extensions.setTitle
import com.julianozanella.springandroidapp.viewModel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_payment.*

class PaymentFragment : Fragment() {

    private var pedidoDTO: PedidoDTO? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(activity!!)[OrderViewModel::class.java]
        pedidoDTO = viewModel.pedidoDTO.value
        var typeString = "pagamentoComCartao"
        rd_group_payment_type.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_card) {
                tv_portions_in_card.visibility = View.VISIBLE
                sp_portions.visibility = View.VISIBLE
                typeString = "pagamentoComCartao"
            } else {
                tv_portions_in_card.visibility = View.GONE
                sp_portions.visibility = View.GONE
                typeString = "pagamentoComBoleto"
            }
        }
        bt_to_order_confirmation.setOnClickListener {
            val portions = sp_portions.selectedItemPosition + 1
            pedidoDTO?.pagamento = PagamentoDTO(portions, typeString)
            //TODO("Confirmar pedido")
            Log.d("Pedido", pedidoDTO.toString())
        }
    }


    override fun onResume() {
        super.onResume()
        setTitle(R.string.title_payment)
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
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }
}
