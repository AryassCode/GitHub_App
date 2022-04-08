package com.udinn.submission2.Main.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavAccountDao {
        @Insert
        fun addToFav(favAccount: FavAccount)

        @Query("SELECT  * FROM favorite_account")
        fun getFavAccount(): LiveData<List<FavAccount>>

        @Query("SELECT count(*) FROM favorite_account WHERE favorite_account.id = :id")
        fun checkAccount(id: Int): Int

        @Query("DELETE FROM favorite_account WHERE favorite_account.id = :id")
        fun removeFromAccount(id: Int):Int

        @Query("SELECT  * FROM favorite_account")
        fun getFindAll(): Cursor
}