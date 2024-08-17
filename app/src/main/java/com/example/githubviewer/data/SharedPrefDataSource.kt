package com.example.githubviewer.data

import android.content.Context

private const val KEY = "TOKEN"

class SharedPrefDataSource(context: Context) {

    private val sharedPref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    fun saveToken(token : String) {
        editor.putString(KEY, token)
        editor.apply()
    }

    fun getToken() : String? {
        return sharedPref.getString(KEY, null)
    }

    fun clear() {
        editor.remove(KEY)
        editor.apply()
    }

}