package com.example.desenrolaai.screens.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    init{
        email.value = ""
        password.value = ""
    }

    fun isEmailValid() : Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }
}