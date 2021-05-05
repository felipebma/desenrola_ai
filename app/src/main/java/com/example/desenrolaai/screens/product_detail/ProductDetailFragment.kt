package com.example.desenrolaai.screens.product_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentProductDetailBinding
import com.example.desenrolaai.model.enums.ProductDetailStatus
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var viewModel: ProductDetailViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        val pos = LatLng(viewModel.product.value?.latitude!!, viewModel.product.value?.longitude!!)
        googleMap.addMarker(MarkerOptions().position(pos))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15.0f))
    }

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
        viewModel.requestSaveSuccess.observe(viewLifecycleOwner, Observer {
            viewModel.updateProduct()
            this.findNavController().navigate(R.id.action_productDetailFragment_to_productsFragment)
        })
        viewModel.requestSaveFailed.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                context,
                "Ooops, não conseguimos salvar seu produto no momento, tente novamente mais tarde",
                Toast.LENGTH_SHORT
            ).show()
        })
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        binding.productDetailViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.confirmButton.setOnClickListener {
            Log.i("Product", "CLICOU")
            if (viewModel.getStatus() != ProductDetailStatus.DETAIL && !viewModel.validProduct()) {
                Toast.makeText(context, "Dados inválidos", Toast.LENGTH_SHORT)
            } else {
                viewModel.switchStatus()
                binding.executePendingBindings()
                changeVisibility()
            }
        }
        binding.rejectButton.setOnClickListener {
            when (viewModel.getStatus()) {
                ProductDetailStatus.ADD -> {
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_productDetailFragment_to_productsFragment)
                }
                ProductDetailStatus.EDIT -> {
                    viewModel.setStatus(ProductDetailStatus.DETAIL)
                }
            }
            changeVisibility()
        }
        changeVisibility()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
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
                if (viewModel.getStatus() == ProductDetailStatus.ADD) {
                    (activity as AppCompatActivity).supportActionBar?.title = "Adicionar Produto"
                } else {
                    (activity as AppCompatActivity).supportActionBar?.title = "Editar Produto"
                }
            }
        }
    }
}