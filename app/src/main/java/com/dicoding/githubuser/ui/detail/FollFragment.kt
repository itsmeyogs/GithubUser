package com.dicoding.githubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.FragmentFollBinding

class FollFragment : Fragment() {

    private lateinit var binding: FragmentFollBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFoll.layoutManager = LinearLayoutManager(requireActivity())

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1) {
            detailViewModel.listFollowers.observe(viewLifecycleOwner) {
                setData(it)
            }
        } else {
            detailViewModel.listFollowing.observe(viewLifecycleOwner) {
                setData(it)
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (position == 1) {
            detailViewModel.loadFollowers(username)
        } else {
            detailViewModel.loadFollowing(username)
        }
    }

    private fun setData(listData: List<ItemsItem>) {
        val adapter = FollAdapter()
        adapter.submitList(listData)
        binding.rvFoll.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFoll.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}