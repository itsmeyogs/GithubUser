package com.dicoding.githubuser.data.local.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [FavUser::class], version = 1, exportSchema = false)
abstract class FavUserDatabase : RoomDatabase() {
    abstract fun favUserDao(): FavUserDao

    companion object {
        @Volatile
        private var instance: FavUserDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): FavUserDatabase {
            if (instance ==null){
                synchronized(FavUserDatabase::class.java){
                    instance = Room.databaseBuilder(context.applicationContext,
                        FavUserDatabase::class.java, "fav_user.db")
                        .build()
                }
            }
            return instance as FavUserDatabase
        }
    }
}