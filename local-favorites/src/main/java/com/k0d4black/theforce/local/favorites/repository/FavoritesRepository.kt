/**
 *
 * Copyright 2020 David Odari
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *            http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 **/
package com.k0d4black.theforce.local.favorites.repository

import com.k0d4black.theforce.local.favorites.dao.FavoritesDao
import com.k0d4black.theforce.local.favorites.mappers.toDomain
import com.k0d4black.theforce.domain.models.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesRepository(private val favoritesDao: FavoritesDao) : IFavoritesRepository {

    override fun getAllFavorites(): Flow<List<Favorite>> = flow {
        val favs = favoritesDao.getAll()
        emit(favs.map { it.toDomain() })
    }

    @Suppress("SENSELESS_COMPARISON")
    override fun getFavoriteByName(name: String): Flow<Favorite?> = flow {
        val fav = favoritesDao.getByName(name)
        //If value not in table will be null
        if (fav != null)
            emit(fav.toDomain())
        else
            emit(null)
    }

    override fun deleteFavoriteByName(name: String): Flow<Int> = flow {
        val rowsAffected = favoritesDao.deleteByName(name)
        emit(rowsAffected)
    }

    override fun deleteAllFavorites(): Flow<Int> = flow {
        val rowsAffected = favoritesDao.deleteAll()
        emit(rowsAffected)
    }

    override fun insertFavorite(favorite: Favorite): Flow<Result> = flow {
        val result = favoritesDao.insert(favorite)
        emit(result)
    }

}