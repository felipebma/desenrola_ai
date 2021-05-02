package com.example.desenrolaai.screens.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.desenrolaai.MainActivity
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_in,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        binding.emailEdit.doOnTextChanged { text, start, before, count ->
            viewModel.email.value = text.toString()
        }

        binding.passwordEdit.doOnTextChanged { text, start, before, count ->
            viewModel.password.value = text.toString()
        }

        binding.signUpText.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_signUpFragment)
        }

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("Usuário atual:", currentUser.toString())
            onSuccessfulSignIn()
        }

        updateEmailAndPassword()
        binding.signInButton.setOnClickListener { signIn(auth, db) }
        return binding.root
    }

    private fun signIn(auth: FirebaseAuth, databaseFirestore: FirebaseFirestore) {

        viewModel.email.value = binding.emailEdit.text.toString()
        viewModel.password.value = binding.passwordEdit.text.toString()

        if (viewModel.email.value == "" && viewModel.password.value == "") {
            Toast.makeText(
                activity, "Algo deu errado, cheque suas credenciais e tente novamente!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!viewModel.isEmailValid()) {
            Toast.makeText(
                activity, "Email inválido",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        try {
            auth.signInWithEmailAndPassword(viewModel.email.value!!, viewModel.password.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity, "Login realizado",
                            Toast.LENGTH_SHORT
                        ).show()
                        databaseFirestore.collection("users")
                            .whereEqualTo("email", viewModel.email.value)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    Log.d("Id user:", "${document.id} => ${document.data}")
                                }
                                onSuccessfulSignIn()
                            }
                            .addOnFailureListener { exception ->
                                Log.w("Error", "Error getting documents: ", exception)
                            }
                    } else {
                        Log.i("Failed login email", viewModel.email.value!!)
                        Log.i("Failed login password", viewModel.password.value!!)
                        Toast.makeText(
                            activity, "Falha ao fazer login, tente novamente!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } catch (err: Error) {
            Toast.makeText(
                activity, "Algo deu errado, cheque suas credenciais e tente novamente!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateEmailAndPassword() {
        Log.i("MainActivity", viewModel.email.value!!)
        binding.emailEdit.setText(viewModel.email.value)
        binding.passwordEdit.setText(viewModel.password.value)
    }

    private fun onSuccessfulSignIn() {
        val intent = Intent(activity, MainActivity::class.java)
        activity?.finish()
        startActivity(intent)
    }
}
