package com.example.desenrolaai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.desenrolaai.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    data class User(
        var name: String = "",
        var email: String = "",
        var address: String = "",
        var password: String = ""
    )

    private val user = User()
    lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        )
        binding.signUpText.setOnClickListener {
            it.findNavController().navigate(R.id.action_signUpFragment_to_titleFragment)
        }
        binding.signUpButton.setOnClickListener { signUp() }
        return binding.root
    }

    private fun signUp() {
        user?.name = binding.nameEdit.text.toString()
        user?.address = binding.addressEdit.text.toString()
        user?.email = binding.emailEdit.text.toString()
        user?.password = binding.passwordEdit.text.toString()
        Log.d("User", user.toString())
    }
}