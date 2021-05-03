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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentBorrowDetailBinding
import com.example.desenrolaai.model.enums.BorrowDetailFragmentStatus

class BorrowDetailFragment : Fragment() {

    private lateinit var binding: FragmentBorrowDetailBinding
    private lateinit var viewModel: BorrowDetailViewModel

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