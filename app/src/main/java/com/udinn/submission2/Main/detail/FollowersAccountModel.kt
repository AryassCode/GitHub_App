package com.udinn.submission2.Main.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udinn.submission2.Main.api.RetrofitAccount
import com.udinn.submission2.Main.main.MainViewModel
import com.udinn.submission2.Main.model.Account
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersAccountModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<Account>>()

    fun setListFollowers(username: String) {
        RetrofitAccount.apiInstance
            .getAccountFollowers(username)
            .enqueue(object : Callback<ArrayList<Account>> {
                override fun onResponse(
                    call: Call<ArrayList<Account>>,
                    response: Response<ArrayList<Account>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    } else {
                        Log.e(MainViewModel.TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ArrayList<Account>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<Account>> {
        return listFollowers
    }
}