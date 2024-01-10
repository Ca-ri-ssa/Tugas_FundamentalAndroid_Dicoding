package com.carissa.fundamental.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.carissa.fundamental.database.FavoriteUserEntity
import com.carissa.fundamental.repository.FavoriteUserRepository

class FavoriteUserAddUpdateViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUserEntity) {
        favoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUserEntity) {
        favoriteUserRepository.delete(favoriteUser)
    }
}