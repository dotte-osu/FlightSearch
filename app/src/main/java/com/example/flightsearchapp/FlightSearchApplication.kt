package com.example.flightsearchapp

import android.app.Application
import com.example.flightsearchapp.data.AppDatabase

class FlightSearchApplication: Application(){
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}

