package com.example.desenrolaai.screens.borrow_detail

import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentBorrowDetailBinding
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.enums.BorrowDetailFragmentStatus
import com.example.desenrolaai.screens.map.infoAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class BorrowDetailFragment : Fragment() {

    private lateinit var binding: FragmentBorrowDetailBinding
    private lateinit var viewModel: BorrowDetailViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        val pos = LatLng(viewModel.getLatitude()!!, viewModel.getLongitude()!!)
        googleMap.addMarker(MarkerOptions().position(pos))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15.0f))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_borrow_detail, container, false)
        val viewModelFactory = BorrowDetailViewModelFactory(BorrowDetailFragmentArgs.fromBundle(requireArguments()).borrow, BorrowDetailFragmentArgs.fromBundle(requireArguments()).product)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BorrowDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.confirmButton.setOnClickListener {
            viewModel.switchStatus()
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

    private fun changeVisibility(){
        when(viewModel.status){
            BorrowDetailFragmentStatus.DETAIL -> {
                binding.confirmButton.text = getString(R.string.order_borrow)
                binding.confirmButton.visibility = View.VISIBLE
                binding.confirmBorrowExtra.visibility = View.GONE
            }
            BorrowDetailFragmentStatus.CONFIRM -> {
                binding.confirmButton.text = getString(R.string.confirm_borrow)
                binding.confirmButton.visibility = View.VISIBLE
                binding.confirmBorrowExtra.visibility = View.VISIBLE
            }
            BorrowDetailFragmentStatus.READ -> {
                binding.confirmButton.visibility = View.GONE
                binding.confirmBorrowExtra.visibility = View.GONE
            }
        }
    }
}