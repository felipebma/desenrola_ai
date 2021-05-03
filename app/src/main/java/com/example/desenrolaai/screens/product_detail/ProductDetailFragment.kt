package com.example.desenrolaai.screens.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        val viewModelFactory =
            ProductDetailViewModel.ProductDetailViewModelFactory(ProductDetailFragmentArgs.fromBundle(requireArguments()).product, ProductDetailFragmentArgs.fromBundle(requireArguments()).status)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductDetailViewModel::class.java)
        binding.productDetailViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }
}