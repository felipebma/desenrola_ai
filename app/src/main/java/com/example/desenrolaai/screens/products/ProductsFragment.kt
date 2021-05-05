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
import androidx.navigation.fragment.NavHostFragment
import com.example.desenrolaai.ProductAdapter
import com.example.desenrolaai.ProductListener
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentProductsBinding
import com.example.desenrolaai.model.enums.ProductDetailStatus

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        binding.viewModel = viewModel
        val adapter = ProductAdapter(ProductListener {
            Log.d("MovingWith", it.toString())
            val action = ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(
                it,
                ProductDetailStatus.DETAIL
            )
            NavHostFragment.findNavController(this).navigate(action)
        })
        binding.productList.adapter = adapter
        viewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        Log.i("ProductFragment", adapter.currentList.toString())
        binding.lifecycleOwner = this


        return binding.root
    }
}