package com.julianozanella.springandroidapp.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.view.util.IReplaceFragAndTitle

class PickAddressFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
