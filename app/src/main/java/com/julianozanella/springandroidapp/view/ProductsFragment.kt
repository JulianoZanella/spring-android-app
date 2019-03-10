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
import com.julianozanella.springandroidapp.view.adapter.ProductsAdapter
import com.julianozanella.springandroidapp.viewModel.CategoriesViewModel
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment() {

    private var categoryId: String? = null
    private var title: String? = null
    private lateinit var viewModel: CategoriesViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!)[CategoriesViewModel::class.java]
        val adapter = ProductsAdapter(activity!!)
        viewModel.getProdutos(categoryId!!).observe(this, Observer<Categoria> {
            if (it?.produtos != null) {
                adapter.items = it.produtos.asList()
            }
        })
        rv_products.layoutManager = LinearLayoutManager(activity)
        rv_products.adapter = adapter
        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_products.addItemDecoration(divider)
    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            (activity as MainActivity).updateToolbarTitleInFragment(title ?: "Produto")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getString(CATEGORY_ID)
            title = it.getString(TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }


    companion object {

        private const val CATEGORY_ID = "categoryId"
        private const val TITLE = "title"

        @JvmStatic
        fun newInstance(categoryId: String, title: String) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_ID, categoryId)
                    putString(TITLE, title)
                }
            }
    }
}
