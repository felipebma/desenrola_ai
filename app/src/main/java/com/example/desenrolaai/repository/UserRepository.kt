package com.example.desenrolaai.repository

import androidx.lifecycle.LiveData
import com.example.desenrolaai.data.db.entity.UserEntity

interface UserRepository {

    suspend fun insertUser(name: String, email: String, latitude: Double, longitude: Double):Long

    suspend fun getOneUser(email: String): LiveData<UserEntity>
}