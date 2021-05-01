package com.example.desenrolaai.firebase


import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Error

class Firebase {
    private var auth: FirebaseAuth? = null;
    private var db: FirebaseFirestore? = null;

    init {
        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    fun currentUser(): FirebaseUser? {
        return auth?.currentUser;
    }

    suspend fun signIn(email: String, password: String): Boolean {
        try {
            auth?.signInWithEmailAndPassword(email, password)?.await()
            return true;
        } catch (err: Error) {
            Log.e("Erro no Firebase:",err.toString());
            return false;
        }
    }


    fun register(username: String, email: String, address: String, password: String): Boolean {
        try {
            var result = false
            val profile: MutableMap<String, Any> = HashMap()
            profile["name"] = username
            profile["address"] = address
            profile["email"] = email

            auth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                result = task.isSuccessful;

                if (task.isSuccessful) {
                    db?.collection("users")
                        ?.add(profile)
                        ?.addOnSuccessListener { documentReference ->
                            Log.d(
                                "Firebase",
                                "DocumentSnapshot added with ID: " + documentReference.id
                            )
                        }
                        ?.addOnFailureListener { e ->
                            Log.w(
                                "Firebase",
                                "Error adding document",
                                e
                            )
                        }
                }
            }

            return result;
        } catch (err: Error) {
            Log.d("Erro no Firebase:", err.toString());
            return false;
        }
    }

    fun singOut(){
        auth?.signOut();
    }
}