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
import androidx.navigation.fragment.findNavController
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
        binding = DataBindingUtil.inflate(
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
        user.latitude = if(binding.latitudeEdit.text.toString().isEmpty()) 0.0 else binding.latitudeEdit.text.toString().toDouble()
        user.longitude = if(binding.longitudeEdit.text.toString().isEmpty()) 0.0 else binding.longitudeEdit.text.toString().toDouble()
        user.email = binding.emailEdit.text.toString()
        password = binding.passwordEdit.text.toString()

        if(!isUserValid()) return

        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profile: MutableMap<String, Any> = HashMap()
                    profile["name"] = user.name!!
                    profile["latitude"] = user.latitude!!
                    profile["longitude"] = user.longitude!!
                    profile["email"] = user.email!!

                    databaseFirestore.collection("users")
                        .document(user.email!!)
                        .set(profile)
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "Usuário cadastrado com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                            this.findNavController().navigate(R.id.action_signUpFragment_to_titleFragment)
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error adding document", e)
                        }
                } else {
                    Toast.makeText(
                        activity, "Falha ao se cadastrar, tente novamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


    }

    fun isUserValid(): Boolean {
        return validName() && validEmail() && validPosition() && validPassword()
    }

    fun validName(): Boolean {
        val valid = user.name?.length!! > 0
        if (!valid) Toast.makeText(context, "Nome inválido", Toast.LENGTH_SHORT).show()
        return valid
    }

    fun validEmail(): Boolean {
        val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()
        if (!valid) Toast.makeText(context, "Email inválido", Toast.LENGTH_SHORT).show()
        return valid
    }

    fun validPosition(): Boolean {
        val valid =
            user.latitude!! <= 90 && user.latitude!! >= -90 && user.longitude!! <= 180 && user.longitude!! >= -180
        if (!valid) Toast.makeText(
            context,
            "Valores inválidos de Latitude/Longitude",
            Toast.LENGTH_SHORT
        ).show()
        return valid
    }

    fun validPassword(): Boolean {
        val valid = password?.length!! > 5
        if (!valid) Toast.makeText(context, "A senha deve conter mais de 6 caracteres", Toast.LENGTH_SHORT).show()
        return valid
    }


}