package com.example.desenrolaai.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.desenrolaai.data.db.entity.UserEntity

@Dao
interface UserDAO {
    @Insert
    suspend fun insert(user: UserEntity): Long

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun getOne(email: String): LiveData<UserEntity>
}
