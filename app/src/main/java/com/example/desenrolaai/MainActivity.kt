package com.example.desenrolaai

import androidx.appcompat.app.AppCompatActivity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}