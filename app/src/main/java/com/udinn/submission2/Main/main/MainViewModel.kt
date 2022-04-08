package com.udinn.submission2.Main.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udinn.submission2.Main.model.Account
import com.udinn.submission2.Main.model.AccountResponse
import com.udinn.submission2.Main.api.RetrofitAccount
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    val listAccount = MutableLiveData<ArrayList<Account>>()

    fun setSearchAccount(query: String) {
        RetrofitAccount.apiInstance
            .getSearchAccount(query)
            .enqueue(object : Callback<AccountResponse> {
                override fun onResponse(
                    call: Call<AccountResponse>,
                    response: Response<AccountResponse>
                ) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        listAccount.value = response.body()?.items
                    } else {
                        Log.e(MainViewModel.TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<Account>> {
        return listAccount
    }
}