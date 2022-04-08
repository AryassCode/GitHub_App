package com.udinn.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var list = MutableLiveData<ArrayList<Account>>()

    fun setFavoriteAccount(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteAccountColims.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listConverted = MappingHelper.mapCursorArrayList(cursor)
        list.postValue(listConverted)
    }


    fun getFavAccount(): LiveData<ArrayList<Account>> {
        return list
    }
}