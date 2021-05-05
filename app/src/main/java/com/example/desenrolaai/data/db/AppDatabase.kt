package com.example.desenrolaai.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.desenrolaai.data.db.dao.UserDAO
import com.example.desenrolaai.data.db.entity.UserEntity
import kotlinx.serialization.json.Json.Default.context

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDAO: UserDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            synchronized(this){
                var instance: AppDatabase? = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                }

                return instance
            }
        }
    }
}