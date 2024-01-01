package com.dicoding.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.local.room.FavUser
import com.dicoding.githubuser.data.local.room.FavUserDao
import com.dicoding.githubuser.data.local.room.FavUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mFavUserDao: FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserDatabase.getInstance(application)
        mFavUserDao = db.favUserDao()
    }
    fun getFavUser(): LiveData<List<FavUser>> = mFavUserDao.getFavUser()

    fun getFavUserByUsername(username: String): LiveData<FavUser> {
        return mFavUserDao.getFavUserByUsername(username)
    }

    fun insert(favUser: FavUser) {
        executorService.execute { mFavUserDao.insertFavUser(favUser)}
    }
    fun delete(favUser: FavUser) {
        executorService.execute { mFavUserDao.deleteFavUser(favUser) }
    }

}