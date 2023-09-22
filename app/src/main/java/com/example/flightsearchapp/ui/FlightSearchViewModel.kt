package com.example.flightsearchapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
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

class FlightSearchViewModel(
    private val flightScheduleDao: FlightScheduleDao
): ViewModel()  {

    private val _userInputFlow = MutableStateFlow<String>("")
    val userInput: StateFlow<String> get() = _userInputFlow

    fun update(userInput: String) {
        _userInputFlow.value = userInput
    }

    fun clear() {
        _userInputFlow.value = ""
    }

    private val _currentAirPort = MutableStateFlow<Airport?>(null)
    val currentAirport: StateFlow<Airport?> get() = _currentAirPort

    fun setCurrentAirport(airport: Airport) {
        _currentAirPort.value = airport
    }

    fun getAirportByName(userInput: String): Flow<Airport> =
        flightScheduleDao.getAirportByName(userInput)

    fun getFlightListByAirport(userInput: String): Flow<List<Airport>> =
        flightScheduleDao.getFlightListByAirport(userInput)

    fun getIataCodeByName(userInput: String): Flow<List<Airport>> {
        return flightScheduleDao.getIataCodeByName(userInput)
    }


//    fun getAirportByName(): Flow<List<Airport>> = flightScheduleDao.getAirportByName(_userInputFlow.value)
//    fun getIataCodeByName(): Flow<List<Airport>> = flightScheduleDao.getIataCodeByName(_userInputFlow.value)
//
//    var uiState: StateFlow<UiState> =
//        flightScheduleDao.getIataCodeByName(userInput)
//            .filterNotNull()
//            .map {
//                Log.d("FlightSearchViewModel2","uiState")
//                Log.d("FlightSearchViewModel2","${_uiState.value.userInput}")
//                UiState(airports = it)
//            }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = UiState()
//            )

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                FlightSearchViewModel(flightStateApplication().database.flightScheduleDao())
            }
        }
    }
}

fun CreationExtras.flightStateApplication(): FlightSearchApplication =
    (this[APPLICATION_KEY] as FlightSearchApplication)


