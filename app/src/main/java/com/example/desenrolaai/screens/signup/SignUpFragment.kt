package com.example.desenrolaai.screens.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.desenrolaai.R
import com.example.desenrolaai.databinding.FragmentSignUpBinding
import com.example.desenrolaai.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private lateinit var user: User
    private var mAuth: FirebaseAuth? = null
    private lateinit var password: String

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

        user = User()

        mAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()





        binding.signInButton.setOnClickListener { signUp(mAuth!!, db) }
        return binding.root
    }

    private fun signUp(auth: FirebaseAuth, databaseFirestore: FirebaseFirestore) {
        user.name = binding.nameEdit.text.toString()
        user.latitude = binding.latitudeEdit.text.toString().toDouble()
        user.longitude = binding.longitudeEdit.text.toString().toDouble()
        user.email = binding.emailEdit.text.toString()
        password = binding.passwordEdit.text.toString()

        val isValid = isEmailValid(user.email!!)

        if (!isValid) {
            Toast.makeText(
                activity, "Email inválido",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profile: MutableMap<String, Any> = HashMap()
                    profile["name"] = user.name!!
                    profile["latitude"] = user.latitude!!
                    profile["longitude"] = user.longitude!!
                    profile["email"] = user.email!!

                    databaseFirestore.collection("users")
                        .add(profile)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                "LUCAS",
                                "DocumentSnapshot added with ID: " + documentReference.id
                            )
                        }
                        .addOnFailureListener { e -> Log.w("Firebase", "Error adding document", e) }

                    Toast.makeText(
                        activity, "Cadastro criado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LUCAS", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity, "Falha ao se cadastrar, tente novamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }



    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}