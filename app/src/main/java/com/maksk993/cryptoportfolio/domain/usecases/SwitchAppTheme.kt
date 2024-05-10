package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.repository.ThemeRepository

class SwitchAppTheme(private val repository: ThemeRepository) {
    fun execute(isNightMode: Boolean){
        repository.saveTheme(isNightMode)
    }
}