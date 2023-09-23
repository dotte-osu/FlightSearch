
package com.example.dessertrelease.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.flightsearchapp.data.Favorite
import kotlinx.coroutines.flow.map
import java.io.IOException

/*
 * Concrete class implementation to access data store
 */
class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val DEPART = stringPreferencesKey("DEPART")
        val ARRIVE = stringPreferencesKey("ARRIVE")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveToDataStore(favorite: Favorite) {
        dataStore.edit {
            it[DEPART] = favorite.departureCode
            it[ARRIVE] = favorite.destinationCode
        }
    }

    fun getFromDataStore() = dataStore.data.map {
        Favorite(
            departureCode = it[DEPART]?:"",
            destinationCode = it[ARRIVE]?:"",
        )
    }
}