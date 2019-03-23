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
import com.julianozanella.springandroidapp.domain.Categoria
import com.julianozanella.springandroidapp.extensions.replaceFragment
import com.julianozanella.springandroidapp.extensions.setTitle
import com.julianozanella.springandroidapp.view.adapter.CategoriesAdapter
import com.julianozanella.springandroidapp.viewModel.CategoriesViewModel
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    private lateinit var viewModel: CategoriesViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!)[CategoriesViewModel::class.java]
        val adapter = CategoriesAdapter()
        viewModel.categorias.observe(this, Observer<List<Categoria>> {
            if (it != null) {
                adapter.items = it
            }
        })
        rv_categories.layoutManager = LinearLayoutManager(activity)
        rv_categories.adapter = adapter
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_categories.addItemDecoration(divider)
        adapter.clickListener = object : CategoriesAdapter.CategoryClickListener {
            override fun onClickListener(posicao: Int, view: View?, categoria: Categoria) {
                replaceFragment(
                    ProductsFragment.newInstance(
                        categoria.id,
                        categoria.nome
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.title_fragment_categories)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }
}
