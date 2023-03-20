package com.example.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserSearch(
    @field:SerializedName("items")
    val items: List<UserModel>
)

