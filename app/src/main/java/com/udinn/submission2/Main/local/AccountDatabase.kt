package com.udinn.submission2.Main.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavAccount::class],
    version = 1
)
abstract class AccountDatabase: RoomDatabase() {
    companion object{
        var INSTANCE : AccountDatabase? = null

        fun getDatabase(context: Context): AccountDatabase?{
            if (INSTANCE==null){
                synchronized(AccountDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AccountDatabase::class.java,"account_database").build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun favoriteAccountDAO(): FavAccountDao
}