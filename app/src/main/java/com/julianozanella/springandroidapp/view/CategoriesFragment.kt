package com.julianozanella.springandroidapp.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.domain.Categoria
import com.julianozanella.springandroidapp.view.adapter.CategoriesAdapter
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = CategoriesAdapter()
        rv_categories.layoutManager = LinearLayoutManager(activity)
        rv_categories.adapter = adapter
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_categories.addItemDecoration(divider)

        val list = arrayListOf(Categoria("1", "Informatica"), Categoria("2", "Construção"))
        adapter.items = list

    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            (activity as MainActivity).updateToolbarTitleInFragment(R.string.title_fragment_categories)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }
}
