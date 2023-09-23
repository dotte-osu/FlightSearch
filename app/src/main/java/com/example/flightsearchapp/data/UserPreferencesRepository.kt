package com.example.flightsearchapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
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

    val getFromDataStore: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[SEARCHED_WORD] ?: ""
        }
    suspend fun savePreference(text: String) {
        dataStore.edit { preferences ->
            preferences[SEARCHED_WORD] = text
        }
    }
}