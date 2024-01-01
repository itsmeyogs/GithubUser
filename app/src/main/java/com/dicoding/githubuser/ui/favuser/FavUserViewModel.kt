package com.dicoding.githubuser.ui.favuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.local.room.FavUser
import com.dicoding.githubuser.data.repository.FavUserRepository
import com.dicoding.githubuser.ui.detail.DetailActivity

class FavUserModel(application: Application): ViewModel() {
    private val mFavUserRepository: FavUserRepository =  FavUserRepository(application)


    fun getUserByUsername() = mFavUserRepository.getFavUserByUsername(DetailActivity.uname)

    fun delete(favUser: FavUser){
        mFavUserRepository.delete(favUser)
    }

    fun insert(favUser: FavUser){
        mFavUserRepository.insert(favUser)
    }

    fun getAll(): LiveData<List<FavUser>> {
        return mFavUserRepository.getFavUser()

    }


}