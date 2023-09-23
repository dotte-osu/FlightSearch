package com.example.flightsearchapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dessertrelease.data.UserPreferencesRepository
import com.example.flightsearchapp.data.AppDatabase

private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)
class FlightSearchApplication: Application(){
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

//    lateinit var userPreferencesRepository: UserPreferencesRepository
//    lateinit var database: AppDatabase
//    override fun onCreate() {
//        super.onCreate(saved)
//        userPreferencesRepository = UserPreferencesRepository(dataStore)
//        database = AppDatabase.getDatabase(this)
//    }
}


