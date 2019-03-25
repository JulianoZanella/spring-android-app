package com.julianozanella.springandroidapp.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.extensions.KEY
import com.julianozanella.springandroidapp.extensions.getSharedPreference
import com.julianozanella.springandroidapp.extensions.setTitle
import com.julianozanella.springandroidapp.service.ImageService
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val clienteDTO = activity!!.getSharedPreference(KEY.CLIENT, ClienteDTO::class.java) as ClienteDTO?
        clienteDTO?.let {
            tv_client_name.text = it.nome
            tv_client_email.text = it.email
            ImageService().setClientImage(riv_client, it.id)
        }
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.tittle_profile)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}
