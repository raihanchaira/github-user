package com.example.githubuser.network

import com.example.githubuser.model.DetailUser
import com.example.githubuser.model.UserModel
import com.example.githubuser.model.UserSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @GET("/users")
    @Headers("Authorization: token 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")
    fun getUser() : Call<List<UserModel>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchuser(
        @Query("q") query: String
    ): Call<UserSearch>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username : String
    ) : Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ) : Call<List<UserModel>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<List<UserModel>>


}