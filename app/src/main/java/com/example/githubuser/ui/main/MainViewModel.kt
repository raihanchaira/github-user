package com.example.githubuser.ui.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.model.UserModel
import com.example.githubuser.model.UserSearch
import com.example.githubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _user = MutableLiveData<List<UserModel>>()
    val user: LiveData<List<UserModel>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getDataFromApi()
    }

    private fun getDataFromApi(){
        _isLoading.value = true
        ApiConfig.getApiService().getUser()
            .enqueue(object : Callback<List<UserModel>> {
                override fun onResponse(
                    call: Call<List<UserModel>>,
                    response: Response<List<UserModel>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _user.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getUserSearch(query: String){
        _isLoading.value = true
        ApiConfig.getApiService()
            .getSearchuser(query)
            .enqueue(object : Callback<UserSearch>{
                override fun onResponse(call: Call<UserSearch>, response: Response<UserSearch>) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _user.value = response.body()?.items
                    }
                }
                override fun onFailure(call: Call<UserSearch>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                }

            })
    }






}