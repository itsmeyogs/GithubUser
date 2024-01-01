package com.dicoding.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavUserDao {
    @Query("SELECT * FROM fav_user ORDER BY username DESC")
    fun getFavUser(): LiveData<List<FavUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavUser(favUser: FavUser)

    @Delete
    fun deleteFavUser(favUser: FavUser)

    @Query("SELECT * FROM fav_user WHERE username = :username")
    fun getFavUserByUsername(username: String): LiveData<FavUser>

}