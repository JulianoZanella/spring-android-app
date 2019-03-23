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
import com.julianozanella.springandroidapp.extensions.KEY
import com.julianozanella.springandroidapp.extensions.getSharedPreference
import com.julianozanella.springandroidapp.view.adapter.AddressAdapter
import com.julianozanella.springandroidapp.view.util.IReplaceFragAndTitle
import com.julianozanella.springandroidapp.viewModel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_pick_address.*

class PickAddressFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(activity!!)[OrderViewModel::class.java]
        val adapter = AddressAdapter()
        val email: String? = activity!!.getSharedPreference(KEY.EMAIL, String::class.java) as String?
        if (email != null) {
            viewModel.findByEmail(email).observe(this, Observer {
                if (it?.enderecos != null) {
                    adapter.items = it.enderecos!!.asList()
                }
            })
        }
        rv_address.adapter = adapter
        rv_address.layoutManager = LinearLayoutManager(activity)
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_address.addItemDecoration(divider)
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
