package com.example.flightsearchapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.FlightSearchApplication
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.data.FlightScheduleDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val flightScheduleDao: FlightScheduleDao
): ViewModel()  {

    private val _userInputFlow = MutableStateFlow<String>("")
    val userInput: StateFlow<String> get() = _userInputFlow

    fun update(userInput: String) {
        _userInputFlow.value = userInput
    }


    private val _favorite = MutableStateFlow<Favorite?>(null)
    val favorite: StateFlow<Favorite?>  get() = _favorite
    fun updateFavorite(favorite: Favorite) {
        _favorite.value = favorite
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
                FlightSearchViewModel(flightStateApplication().database.flightScheduleDao())
            }
        }
    }
}

fun CreationExtras.flightStateApplication(): FlightSearchApplication =
    (this[APPLICATION_KEY] as FlightSearchApplication)


