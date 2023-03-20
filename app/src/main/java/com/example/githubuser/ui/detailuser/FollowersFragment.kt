package com.example.githubuser.ui.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.model.UserModel
import com.example.githubuser.ui.main.UserMainAdapter

class FollowersFragment : Fragment() {

    private  lateinit var listUserAdapter : UserMainAdapter
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var username : String
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowersBinding.bind(view)
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                _binding!!.progressBar.visibility = View.VISIBLE
            } else {
                _binding!!.progressBar.visibility = View.GONE
            }
        }

        followersViewModel.listFollowing.observe(viewLifecycleOwner){
            setupRecycleView(it)
        }

        followersViewModel.setFollowing(username)
        followersViewModel.getFollowing().observe(viewLifecycleOwner){ it ->
            if (it != null){
                followersViewModel.listFollowing.observe(viewLifecycleOwner){
                    setupRecycleView(it)
                }
            }
        }
    }

    private fun setupRecycleView(result: List<UserModel>?) {
        listUserAdapter = UserMainAdapter(result)
        binding.apply {
            rvFollowers.setHasFixedSize(true)
            rvFollowers.layoutManager = LinearLayoutManager(requireActivity())
            rvFollowers.adapter = listUserAdapter
        }
        listUserAdapter.setOnItemClickCallback(object : UserMainAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                Toast.makeText(context, "Ini adalah user followers", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}