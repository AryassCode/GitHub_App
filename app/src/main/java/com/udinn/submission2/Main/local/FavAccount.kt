package com.udinn.submission2.Main.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_account")
data class FavAccount(
    val login: String,

    @PrimaryKey
    val id : Int,
    val avatar_url: String
):Serializable
