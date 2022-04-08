package com.udinn.submission2.Main.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.udinn.submission2.Main.local.AccountDatabase
import com.udinn.submission2.Main.local.FavAccountDao

class FavContentProvider : ContentProvider() {

    companion object{
        const val AUTHORITY = "com.udinn.submission2"
        const val TABLE_NAME = "favorite_account"
        const val ID_FAVORITE_ACCOUNT_DATA = 1
        val uriMatcher =UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAVORITE_ACCOUNT_DATA)
        }
    }
    private lateinit var accountDao: FavAccountDao

    override fun onCreate(): Boolean {
        accountDao = context?.let { ctx ->
            AccountDatabase.getDatabase(ctx)?.favoriteAccountDAO()
        }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cuersor: Cursor?
        when (uriMatcher.match(uri)) {
            ID_FAVORITE_ACCOUNT_DATA -> {
                cuersor = accountDao.getFindAll()
                if (context != null) {
                    cuersor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cuersor = null
            }
        }
        return cuersor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}