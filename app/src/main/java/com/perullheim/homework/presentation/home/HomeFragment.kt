package com.perullheim.homework.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perullheim.homework.databinding.FragmentHomeBinding
import com.perullheim.homework.utils.ViewBindingFragment
import com.perullheim.homework.utils.viewLifecycleScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val usersAdapter = UsersListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvUsers.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = usersAdapter
            }

            btnProfile.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
            }
        }

        viewLifecycleScope {
            viewModel.pageData.collect {
                usersAdapter.submitData(it)
            }
        }
    }
}