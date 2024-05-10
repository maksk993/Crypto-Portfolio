package com.maksk993.cryptoportfolio.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.maksk993.cryptoportfolio.domain.repository.ThemeRepository

const val prefName = "SHARED_PREFS"
const val isNightModeKey = "NIGHT_MODE"

class ThemeRepositoryImpl(context: Context) : ThemeRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        prefName, Context.MODE_PRIVATE
    )
    private val editor = sharedPreferences.edit()

    override fun getTheme(): Boolean {
        return sharedPreferences.getBoolean(isNightModeKey, false)
    }

    override fun saveTheme(isNightMode: Boolean) {
        editor.putBoolean(isNightModeKey, isNightMode)
        editor.apply()
    }
}