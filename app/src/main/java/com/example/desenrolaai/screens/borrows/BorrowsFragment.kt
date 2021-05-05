package com.example.desenrolaai.screens.borrows

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
import com.example.desenrolaai.BorrowAdapter
import com.example.desenrolaai.BorrowListener
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentBorrowsBinding

class BorrowsFragment : Fragment() {

    private lateinit var binding: FragmentBorrowsBinding
    private lateinit var viewModel: BorrowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_borrows, container, false)
        viewModel = ViewModelProvider(this).get(BorrowsViewModel::class.java)
        Log.i("Borrow", "Entrou")
        binding.viewModel = viewModel
        val adapter = BorrowAdapter(BorrowListener {
            Log.d("MovingWith", it.toString())
            val action = BorrowsFragmentDirections.actionBorrowsFragmentToBorrowDetailFragment()
            action.borrow = it
            NavHostFragment.findNavController(this).navigate(action)
        })
        viewModel.dataFetched.observe(viewLifecycleOwner, Observer {
                binding.borrowList.adapter = adapter
                viewModel.borrows.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })
                Log.i("BorrowFragment", adapter.currentList.toString())
                binding.lifecycleOwner = this
        })
        return binding.root
    }
}