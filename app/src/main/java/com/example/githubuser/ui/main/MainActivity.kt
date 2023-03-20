package com.example.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.model.UserModel
import com.example.githubuser.ui.detailuser.DetailUserActivity

class MainActivity : AppCompatActivity() {


    private  lateinit var listUserAdapter : UserMainAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding : ActivityMainBinding

    private var TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        mainViewModel.user.observe(this){
            setupRecycleView(it)
        }
    }

    private fun setupRecycleView(result: List<UserModel>?) {
        listUserAdapter = UserMainAdapter(result)
        binding.apply {
            rvUserMain.setHasFixedSize(true)
            rvUserMain.adapter = listUserAdapter
        }

        listUserAdapter.setOnItemClickCallback(object : UserMainAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user : UserModel){
        val intentToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
        intentToDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, user.names)
        startActivity(intentToDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.isBlank()){
                    mainViewModel.getUserSearch(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
}