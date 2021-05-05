package com.example.desenrolaai.screens.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentProductDetailBinding
import com.example.desenrolaai.model.enums.ProductDetailStatus

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory =
            ProductDetailViewModel.ProductDetailViewModelFactory(
                ProductDetailFragmentArgs.fromBundle(
                    requireArguments()
                ).product, ProductDetailFragmentArgs.fromBundle(requireArguments()).status
            )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(ProductDetailViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        binding.productDetailViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.confirmButton.setOnClickListener {
            viewModel.switchStatus(true)
            binding.executePendingBindings()
            changeVisibility()
        }
        binding.rejectButton.setOnClickListener {
            viewModel.switchStatus(false)
            changeVisibility()
        }
        changeVisibility()
        return binding.root
    }

    private fun changeVisibility() {
        when (viewModel.getStatus()) {
            ProductDetailStatus.DETAIL -> {
                binding.nameEdit.visibility = View.GONE
                binding.nameInfo.visibility = View.VISIBLE
                binding.descriptionEdit.visibility = View.GONE
                binding.descriptionInfo.visibility = View.VISIBLE
                binding.categoriesEdit.visibility = View.GONE
                binding.categoriesInfo.visibility = View.VISIBLE
                binding.priceEdit.visibility = View.GONE
                binding.priceInfo.visibility = View.VISIBLE
                binding.latitudeLayout.visibility = View.GONE
                binding.longitudeLayout.visibility = View.GONE
                binding.confirmButton.text = "Editar"
                binding.rejectButton.visibility = View.GONE
            }
            else -> {
                binding.nameEdit.visibility = View.VISIBLE
                binding.nameInfo.visibility = View.GONE
                binding.descriptionEdit.visibility = View.VISIBLE
                binding.descriptionInfo.visibility = View.GONE
                binding.categoriesEdit.visibility = View.VISIBLE
                binding.categoriesInfo.visibility = View.GONE
                binding.priceEdit.visibility = View.VISIBLE
                binding.priceInfo.visibility = View.GONE
                binding.latitudeLayout.visibility = View.VISIBLE
                binding.longitudeLayout.visibility = View.VISIBLE
                binding.rejectButton.visibility = View.VISIBLE
                binding.confirmButton.text = "Salvar"
                binding.rejectButton.text = "Voltar"
                if(viewModel.getStatus() == ProductDetailStatus.ADD){
                    (activity as AppCompatActivity).supportActionBar?.title = "Adicionar Produto"
                }else{
                    (activity as AppCompatActivity).supportActionBar?.title = "Editar Produto"
                }
            }
        }
    }
}