package com.example.desenrolaai.screens.borrow_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentBorrowDetailBinding

class BorrowDetailFragment : Fragment() {

    private lateinit var binding: FragmentBorrowDetailBinding
    private lateinit var viewModel: BorrowDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_borrow_detail, container, false)
        viewModel = ViewModelProvider(this).get(BorrowDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}