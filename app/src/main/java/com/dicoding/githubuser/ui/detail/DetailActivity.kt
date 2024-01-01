package com.dicoding.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.local.room.FavUser
import com.dicoding.githubuser.data.remote.response.DetailUserResponse
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.dicoding.githubuser.ui.favuser.FavUserModel
import com.dicoding.githubuser.ui.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var _activityDetailUserBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favUserViewModel by viewModels<FavUserModel> {
        ViewModelFactory.getInstance(application)
    }
    private var favUser = FavUser()
    var isFound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailUserBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val Uname = intent.getStringExtra(EXTRA_UNAME)
        uname = Uname.toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        SectionsPagerAdapter.username = uname
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()

        detailViewModel.detailUser.observe(this) {
            getDetailUser(it)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val getUserFav = favUserViewModel.getUserByUsername()
        getUserFav.observe(this) {
            val favButton = binding?.addFavorite
            isFound = if (it?.username != null) {
                favButton?.setImageDrawable(
                    ContextCompat.getDrawable(favButton.context, R.drawable.ic_favorited)
                )
                true

            } else {
                favButton?.setImageDrawable(
                    ContextCompat.getDrawable(favButton.context, R.drawable.ic_favorite)
                )
                false
            }
        }

        binding?.addFavorite?.setOnClickListener {
            if (isFound) {
                favUserViewModel.delete(favUser)
                Toast.makeText(
                    this,
                    "Menghapus ${favUser.username} dari Favorite User",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                favUserViewModel.insert(favUser)
                Toast.makeText(
                    this,
                    "Menambah ${favUser.username} ke Favorite User",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun getDetailUser(user: DetailUserResponse) {
        favUser.let {
            favUser.username = user.login
            favUser.avatarUrl = user.avatarUrl
        }
        Glide.with(this@DetailActivity)
            .load(user.avatarUrl)
            .into(binding?.imgDetailUser!!)
        binding?.apply {
            tvNameDetailUser.text = user.name
            tvUnameDetailUser.text = user.login
        }
        val followers = user.followers
        val following = user.following
        binding?.tvFollowerDetailUser!!.text = getString(R.string.followers,followers)
        binding?.tvFollowingDetailUser!!.text = getString(R.string.following, following)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailUserBinding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarUser!!.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_UNAME = "username"
        var uname = "andi"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}