package com.example.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserModel(
	//photo
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	//name
	@field:SerializedName("login")
	val names: String,

	@field:SerializedName("html_url")
	val url: String
)

