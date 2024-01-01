package com.dicoding.githubuser.ui.favuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityFavBinding
import com.dicoding.githubuser.ui.helper.ViewModelFactory
import com.dicoding.githubuser.ui.main.UserAdapter

class FavActivity : AppCompatActivity() {
    private var _activityFavUserBinding: ActivityFavBinding? = null
    private val binding get() = _activityFavUserBinding
    private val favUserViewModel by viewModels<FavUserModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavUserBinding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvUsers?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvUsers?.addItemDecoration(itemDecoration)


        val adapter = UserAdapter()
        binding?.rvUsers?.adapter = adapter

        favUserViewModel.getAll().observe(this) { favUserList ->
            val items = arrayListOf<ItemsItem>()
            favUserList.map {
                val item = ItemsItem(
                    login = it.username,
                    avatarUrl = it.avatarUrl.toString()
                )
                items.add(item)
            }
            setListUserData(items)
        }


    }

    private fun setListUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding?.rvUsers?.adapter = adapter
    }
}