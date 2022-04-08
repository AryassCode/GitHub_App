package com.udinn.submission2.Main.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udinn.submission2.Main.api.RetrofitAccount
import com.udinn.submission2.Main.local.AccountDatabase
import com.udinn.submission2.Main.local.FavAccount
import com.udinn.submission2.Main.local.FavAccountDao
import com.udinn.submission2.Main.model.DetailAccountResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailAccountModel(application: Application) : AndroidViewModel(application) {
    val account = MutableLiveData<DetailAccountResponse>()

    private var accountDAO: FavAccountDao?
    private var accountDb: AccountDatabase?

    init {
        accountDb = AccountDatabase.getDatabase(application)
        accountDAO = accountDb?.favoriteAccountDAO()

    }

    fun setAccountDetail(username: String) {
        RetrofitAccount.apiInstance
            .getAccountDetail(username)
            .enqueue(object : Callback<DetailAccountResponse> {
                override fun onResponse(
                    call: Call<DetailAccountResponse>,
                    response: Response<DetailAccountResponse>
                ) {
                    if (response.isSuccessful) {
                        account.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailAccountResponse>, t: Throwable) {
                    Log.d("failure", t.message!!)
                }

            })
    }

    fun getAccountDetail(): LiveData<DetailAccountResponse> {
        return account
    }
    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var account = FavAccount(
                username,
                id,
                avatarUrl
            )
            accountDAO?.addToFav(account)
        }
    }
    fun checkUser(id: Int) = accountDAO?.checkAccount(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            accountDAO?.removeFromAccount(id)
        }
    }
}