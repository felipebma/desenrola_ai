package com.example.desenrolaai.screens.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.ProductAdapter
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)
        val adapter = ProductAdapter()
        binding.productList.adapter = adapter
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        Log.i("ProductList", "Teste " + viewModel.products.toString())
        viewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })


        return binding.root
    }
}