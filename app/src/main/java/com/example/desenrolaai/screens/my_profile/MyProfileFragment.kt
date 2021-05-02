package com.example.desenrolaai.screens.my_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {

    private lateinit var binding: FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        binding.currNameText.visibility = View.GONE


        return binding.root
    }
}