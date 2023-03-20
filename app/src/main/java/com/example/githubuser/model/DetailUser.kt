package com.example.githubuser.model

import com.google.gson.annotations.SerializedName

data class DetailUser(
    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("followers_url")
    val followers : String,

    @field:SerializedName("following_url")
    val following : String,

    @field:SerializedName("followers")
    val nfollowers: String,

    @field:SerializedName("following")
    val nfollowing : String

)