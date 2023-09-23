package com.example.flightsearchapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.data.UserPreferencesRepository
import com.example.flightsearchapp.FlightSearchApplication
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.data.FlightScheduleDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val flightScheduleDao: FlightScheduleDao,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel()  {

    private val _userInputFlow: StateFlow<String> =
        userPreferencesRepository.getFromDataStore.map { it ->
            it
        }.stateIn(
            scope = viewModelScope,
            // Flow is set to emits value for when app is on the foreground
            // 5 seconds stop delay is added to ensure it flows continuously
            // for cases such as configuration change
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    val userInput: StateFlow<String> get() = _userInputFlow

    fun update(userInput: String) {
        viewModelScope.launch {
            userPreferencesRepository.savePreference(userInput)
        }
    }

    fun getAirportByName(userInput: String): Flow<Airport> =
        flightScheduleDao.getAirportByName(userInput)

    fun getFlightListByAirport(userInput: String): Flow<List<Airport>> =
        flightScheduleDao.getFlightListByAirport(userInput)

    fun getIataCodeByName(userInput: String): Flow<List<Airport>> {
        return flightScheduleDao.getIataCodeByName(userInput)
    }

    fun getAllFavorites():Flow<List<Favorite>>{
        return flightScheduleDao.getAllFavorites()
    }

    suspend fun saveFavorite(departureCode: String, destinationCode: String) {
        Log.d("Log","saveFavorite")
        val tmp = Favorite(
            departureCode = departureCode,
            destinationCode = destinationCode,
        )
        flightScheduleDao.insert(tmp)
    }

    suspend fun removeFavorite(departureCode: String, destinationCode: String) {
        val favorite: Favorite = flightScheduleDao.getSingleFavorite(departureCode.uppercase(), destinationCode.uppercase())
        if(favorite != null)flightScheduleDao.delete(favorite)
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                FlightSearchViewModel(flightStateApplication().database.flightScheduleDao(), flightStateApplication().userPreferencesRepository )
            }
        }
    }
}

fun CreationExtras.flightStateApplication(): FlightSearchApplication =
    (this[APPLICATION_KEY] as FlightSearchApplication)


