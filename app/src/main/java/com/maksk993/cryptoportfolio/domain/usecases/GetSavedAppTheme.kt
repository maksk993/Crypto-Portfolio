package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.repository.ThemeRepository

class GetSavedAppTheme(private val repository: ThemeRepository) {
    fun execute(): Boolean {
        return repository.getTheme()
    }
}