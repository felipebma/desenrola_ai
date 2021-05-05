package com.example.desenrolaai.screens.borrow_detail

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
import androidx.navigation.fragment.findNavController
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentBorrowDetailBinding
import com.example.desenrolaai.model.enums.BorrowDetailFragmentStatus
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_borrow_detail, container, false)
        val viewModelFactory = BorrowDetailViewModelFactory(
            BorrowDetailFragmentArgs.fromBundle(requireArguments()).borrow,
            BorrowDetailFragmentArgs.fromBundle(requireArguments()).product
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(BorrowDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.requestAccepted.observe(viewLifecycleOwner, Observer {
            goToMyBorrows()
        })

        viewModel.requestRefused.observe(viewLifecycleOwner, Observer {
            if(viewModel.requestRefused.value!!){
                Toast.makeText(context, "Ooops, tivemos algum problema em confirmar o seu empréstimo, tente novamente mais tarde", Toast.LENGTH_SHORT)
                viewModel.requestRefused.value = false;
            }
        })

        binding.confirmButton.setOnClickListener {
            Log.i("BorrowDetail", "Clicou")
            when (viewModel.status) {
                BorrowDetailFragmentStatus.DETAIL -> {
                    viewModel.switchStatus()
                    changeVisibility()
                }
                BorrowDetailFragmentStatus.CONFIRM -> {
                    viewModel.setStartDay(binding.startDateEdit.text.toString())
                    viewModel.setEndDay(binding.endDateEdit.text.toString())
                    if (!viewModel.isValidBorrow()) {
                        Toast.makeText(
                            context,
                            "Datas definidas não são válidas",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.switchStatus()
                    }
                }

            }
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
        when (viewModel.status) {
            BorrowDetailFragmentStatus.DETAIL -> {
                binding.confirmButton.text = getString(R.string.order_borrow)
                binding.confirmButton.visibility = View.VISIBLE
                binding.confirmBorrowExtra.visibility = View.GONE
            }
            BorrowDetailFragmentStatus.CONFIRM -> {
                (activity as AppCompatActivity).supportActionBar?.title = "Confirmar Emprestimo"
                binding.confirmButton.text = getString(R.string.confirm_borrow)
                binding.confirmButton.visibility = View.VISIBLE
                binding.confirmBorrowExtra.visibility = View.VISIBLE
                binding.startDateEdit.visibility = View.VISIBLE
                binding.endDateEdit.visibility = View.VISIBLE
                binding.startDateText.visibility = View.GONE
                binding.endDateText.visibility = View.GONE
                binding.statusLayout.visibility = View.GONE
            }
            BorrowDetailFragmentStatus.READ -> {
                (activity as AppCompatActivity).supportActionBar?.title = "Detalhes do Empréstimo"
                binding.confirmButton.visibility = View.GONE
                binding.confirmBorrowExtra.visibility = View.VISIBLE
                binding.startDateEdit.visibility = View.GONE
                binding.endDateEdit.visibility = View.GONE
                binding.startDateText.visibility = View.VISIBLE
                binding.endDateText.visibility = View.VISIBLE
                binding.statusLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun goToMyBorrows(){
        Toast.makeText(context, "Empréstimo solicitado com sucesso", Toast.LENGTH_SHORT)
        this.findNavController().navigate(R.id.action_borrowDetailFragment_to_borrowsFragment)
    }
}