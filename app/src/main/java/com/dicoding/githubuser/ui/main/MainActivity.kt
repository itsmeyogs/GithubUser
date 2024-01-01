package com.dicoding.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.local.datastore.SettingPreference
import com.dicoding.githubuser.data.local.datastore.dataStore
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.ui.favuser.FavActivity
import com.dicoding.githubuser.ui.helper.ViewModelFactory2
import com.dicoding.githubuser.ui.setting.SettingActivity
import com.dicoding.githubuser.ui.setting.SettingViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)


        mainViewModel.listUsers.observe(this) {
            setListUserData(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.setQuery(searchView.text.toString())
                    false
                }
        }

        binding.topAppBar.setOnMenuItemClickListener {menuitem->
            when(menuitem.itemId){
                R.id.m_setting->{
                    val intent = Intent(this@MainActivity, SettingActivity::class.java )
                    startActivity(intent)
                    true
                }
                R.id.m_favorite->{
                    val intent = Intent(this@MainActivity, FavActivity::class.java)
                    startActivity(intent)
                    true
                }
                else->false
            }

        }

        val pref = SettingPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory2(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setListUserData(listUser: List<ItemsItem>){
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding.rvUsers.adapter = adapter
    }



    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}