package com.udinn.submission2.Main.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.udinn.submission2.Main.local.AccountDatabase
import com.udinn.submission2.Main.local.FavAccount
import com.udinn.submission2.Main.local.FavAccountDao

class FavoriteViewModel (application: Application): AndroidViewModel(application){
    private var accountDAO: FavAccountDao?
    private var accountDb: AccountDatabase?

    init {
        accountDb = AccountDatabase.getDatabase(application)
        accountDAO = accountDb?.favoriteAccountDAO()

    }
    fun getFavAccount(): LiveData<List<FavAccount>>?{
        return accountDAO?.getFavAccount()
    }
}