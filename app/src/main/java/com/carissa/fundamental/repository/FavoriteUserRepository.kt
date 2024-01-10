package com.carissa.fundamental.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.carissa.fundamental.database.FavoriteUserDao
import com.carissa.fundamental.database.FavoriteUserEntity
import com.carissa.fundamental.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val favoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        favoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>> = favoriteUserDao.getAllFavoriteUser()

    fun getFavoriteUsername(username: String): LiveData<FavoriteUserEntity> =
        favoriteUserDao.getFavoriteUsername(username)

    fun insert(favoriteUser: FavoriteUserEntity) {
        executorService.execute { favoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUserEntity) {
        executorService.execute { favoriteUserDao.delete(favoriteUser) }
    }

    fun update(favoriteUser: FavoriteUserEntity) {
        executorService.execute { favoriteUserDao.update(favoriteUser) }
    }
}