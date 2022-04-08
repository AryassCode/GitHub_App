package com.udinn.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursorArrayList(cursor: Cursor?):ArrayList<Account>{
        val list = ArrayList<Account>()
        if (cursor!=null){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteAccountColims.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteAccountColims.USERNAME))
                val avatar_url = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteAccountColims.AVATAR_URL))
                list.add(
                    Account(
                        username,
                        id,
                        avatar_url
                    )
                )
            }
        }
        return list
    }
}