package com.example.desenrolaai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.desenrolaai.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    lateinit var binding : FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container, false)
        binding.signUpText.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_signUpFragment)
        }
        return binding.root
    }
}