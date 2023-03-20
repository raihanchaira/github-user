package com.example.githubuser.ui.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.model.UserModel
import com.example.githubuser.ui.main.UserMainAdapter

class FollowingFragment : Fragment() {

    private  lateinit var listUserAdapter : UserMainAdapter
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var username : String
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowingBinding.bind(view)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                _binding!!.progressBar.visibility = View.VISIBLE
            } else {
                _binding!!.progressBar.visibility = View.GONE
            }
        }

        followingViewModel.listFollowing.observe(viewLifecycleOwner){
            setupRecycleView(it)
        }

        followingViewModel.setFollowing(username)
        followingViewModel.getFollowing().observe(viewLifecycleOwner){ it ->
            if (it != null){
                followingViewModel.listFollowing.observe(viewLifecycleOwner){
                    setupRecycleView(it)
                }
            }
        }
    }

    private fun setupRecycleView(result: List<UserModel>?) {
        listUserAdapter = UserMainAdapter(result)
        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(requireActivity())
            rvFollowing.adapter = listUserAdapter
        }
        listUserAdapter.setOnItemClickCallback(object : UserMainAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                Toast.makeText(context, "Ini adalah user following",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}