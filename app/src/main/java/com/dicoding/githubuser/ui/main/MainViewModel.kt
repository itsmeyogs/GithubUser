package com.dicoding.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.remote.response.GithubResponse
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel:ViewModel() {

    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: LiveData<List<ItemsItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _getQuery = MutableLiveData<String?>()
    val getQuery: LiveData<String?> = _getQuery


    companion object{
        private const val TAG = "MainViewModel"
    }


    init {
        getListUsers()

    }


    private fun getListUsers() {
        _isLoading.value = true
        if (getQuery.getValue() == null){
            _getQuery.value = "alex"
        }
        Log.e("QUERY", getQuery.toString())
        val client = ApiConfig.getApiService().getUser(getQuery.getValue().toString())
        client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _listUsers.value = response.body()?.items
                    }
                }else{
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun setQuery(queryData:String?){
        _getQuery.value = queryData
        getListUsers()
        Log.e(TAG, getQuery.getValue().toString())
    }

}