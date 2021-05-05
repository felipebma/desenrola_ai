package com.example.desenrolaai.repository

import androidx.lifecycle.LiveData
import com.example.desenrolaai.data.db.dao.UserDAO
import com.example.desenrolaai.data.db.entity.UserEntity

class DatabaseDataSourceUser(
    private val userDAO: UserDAO
) : UserRepository{
    override suspend fun insertUser(
        name: String,
        email: String,
        latitude: Double,
        longitude: Double
    ): Long {
        val user = UserEntity(
            name = name,
            email = email,
            latitude = latitude,
            longitude = longitude
        )

        return userDAO.insert(user)
    }

    override suspend fun getOneUser(email: String): LiveData<UserEntity> {
        return userDAO.getOne(email)
    }
}