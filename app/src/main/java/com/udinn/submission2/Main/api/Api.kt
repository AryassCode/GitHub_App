package com.udinn.submission2.Main.api

import com.udinn.submission2.Main.model.Account
import com.udinn.submission2.Main.model.AccountResponse
import com.udinn.submission2.Main.model.DetailAccountResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET ("search/users")
    @Headers ("Authorization: token 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")
    fun getSearchAccount(
        @Query("q") query: String
    ): Call<AccountResponse>

    @GET("users/{username}")
    @Headers ("Authorization: token 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")
    fun getAccountDetail(
        @Path("username") username: String
    ): Call<DetailAccountResponse>

    @GET("users/{username}/followers")
    @Headers ("Authorization: token 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")
    fun getAccountFollowers(
        @Path("username") username: String
    ): Call<ArrayList<Account>>

    @GET("users/{username}/following")
    @Headers ("Authorization: token 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")
    fun getAccountFollowing(
        @Path("username") username: String
    ): Call<ArrayList<Account>>

}