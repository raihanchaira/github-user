package com.example.githubuser.ui.detailuser

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.model.UserModel
import com.example.githubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val _listFollowing = MutableLiveData<List<UserModel>>()
    val listFollowing: LiveData<List<UserModel>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFollowing(username : String){
        _isLoading.value = true
        ApiConfig.getApiService().getFollowers(username)
            .enqueue(object : Callback<List<UserModel>> {
                override fun onResponse(
                    call: Call<List<UserModel>>,
                    response: Response<List<UserModel>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getFollowing() : LiveData<List<UserModel>> {
        return _listFollowing
    }
}