/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.dessertrelease.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

/*
 * Concrete class implementation to access data store
 */
class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val SEARCHED_WORD = stringPreferencesKey("SEARCHED_WORD")
        const val TAG = "UserPreferencesRepo"
    }

    fun getFromDataStore() = dataStore.data.map { it }

    suspend fun savePreference(text: String) {
        dataStore.edit { preferences ->
            preferences[SEARCHED_WORD] = text
        }
    }
}