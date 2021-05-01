package com.example.desenrolaai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.desenrolaai.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Error


class SignInFragment : Fragment() {

    data class Login(
        var email: String = "",
        var password: String = ""
    )

    lateinit var binding : FragmentSignInBinding
    private val login = Login()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSignInBinding>(
            inflater,
            R.layout.fragment_sign_in,
            container,
            false
        )
        binding.signUpText.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_signUpFragment)
        }

        val auth = FirebaseAuth.getInstance();
        val db = FirebaseFirestore.getInstance();

        val currentUser = auth!!.currentUser
        if(currentUser != null){
            Log.d("Usuário atual:", currentUser.toString())

            //ir para dentro do app
            val intent = Intent(getActivity(), MainActivity::class.java)
            this.activity?.finish();
            startActivity(intent);
        }

        binding.signInButton.setOnClickListener {signIn(auth!!, db)}
        return binding.root
    }

    private fun signIn(auth: FirebaseAuth, databaseFirestore: FirebaseFirestore) {

        login?.email = binding.emailEdit.text.toString()
        login?.password = binding.passwordEdit.text.toString()

        if (login.email == "" && login.password == ""){
            Toast.makeText(
                getActivity(), "Algo deu errado, cheque suas credenciais e tente novamente!",
                Toast.LENGTH_SHORT
            ).show()
            return;
        }

        val isValid = isEmailValid(login.email)

        if(!isValid){
            Toast.makeText(
                getActivity(), "Email inválido",
                Toast.LENGTH_SHORT
            ).show()
            return;
        }



        try {
            auth.signInWithEmailAndPassword(login.email, login.password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        getActivity(), "Login realizado",
                        Toast.LENGTH_SHORT
                    ).show()
                    databaseFirestore.collection("users")
                        .whereEqualTo("email", login.email)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                Log.d("Id user:", "${document.id} => ${document.data}")
                            }
                            //ir para dentro do app
                            val intent = Intent(getActivity(), MainActivity::class.java)
                            this.activity?.finish();
                            startActivity(intent);
                        }
                        .addOnFailureListener { exception ->
                            Log.w("Erro", "Error getting documents: ", exception)
                        }
                } else {
                    Toast.makeText(
                        getActivity(), "Falha ao fazer login, tente novamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (err: Error){
            Toast.makeText(
                getActivity(), "Algo deu errado, cheque suas credenciais e tente novamente!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
