package com.maksk993.cryptoportfolio.domain.repository

interface ThemeRepository {
    fun getTheme(): Boolean
    fun saveTheme(isNightMode: Boolean)
}