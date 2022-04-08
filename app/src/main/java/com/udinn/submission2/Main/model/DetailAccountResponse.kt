package com.udinn.submission2.Main.model

data class DetailAccountResponse(
    val login : String,
    val id : Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url : String,
    val name : String,
    val followers: String,
    val following : String,
    val location : String,
    val company : String,
    val public_repos:String
)
