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
package com.k0d4black.theforce.di

import androidx.room.Room
import com.k0d4black.theforce.data.local.dao.FavoritesDao
import com.k0d4black.theforce.data.local.repository.FavoritesRepository
import com.k0d4black.theforce.domain.repository.IFavoritesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val localDataSourceModule = module {

    single<IFavoritesRepository> { FavoritesRepository(favoritesDao = get()) }

    single {
        Room
            .databaseBuilder(androidContext(), com.k0d4black.theforce.local.core.CharactersDatabase::class.java, "character_db")
            .build()
    }

    single { provideFavoritesDao(db = get()) }


}

internal fun provideFavoritesDao(db: com.k0d4black.theforce.local.core.CharactersDatabase): FavoritesDao = db.favoritesDao()