package com.dicoding.githubuser.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = FollFragment()
        fragment.arguments= Bundle().apply {
            putInt(FollFragment.ARG_POSITION,position+1)
            putString(FollFragment.ARG_USERNAME,username)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }

    companion object {
        var username:String = ""
    }
}