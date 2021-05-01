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
import com.example.desenrolaai.databinding.FragmentTitleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class TitleFragment : Fragment() {

    data class Login(
        var email: String = "",
        var password: String = ""
    )

    lateinit var binding : FragmentTitleBinding
    private var mAuth: FirebaseAuth? = null
    private val login = Login()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )
        binding.signUpText.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_signUpFragment)
        }

        mAuth = FirebaseAuth.getInstance();
        val db = FirebaseFirestore.getInstance()

        val currentUser = mAuth!!.currentUser
        if(currentUser != null){
            Log.d("Usuário atual:", currentUser.toString())

            //ir para dentro do app
            val intent = Intent(getActivity(), MainActivity::class.java)
            startActivity(intent);
        }

        binding.signInButton.setOnClickListener {signIn(mAuth!!, db)}
        return binding.root
    }

    private fun signIn(auth: FirebaseAuth, databaseFirestore: FirebaseFirestore) {

        login?.email = binding.emailEdit.text.toString()
        login?.password = binding.passwordEdit.text.toString()

        auth.signInWithEmailAndPassword(login.email, login.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
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
    }
}
