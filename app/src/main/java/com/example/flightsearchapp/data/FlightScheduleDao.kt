package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the FlightSchedule database
 */
@Dao
interface FlightScheduleDao {

    @Query("Select * from airport where iata_code = :name")
    fun getAirportByName(name: String): Flow<Airport>

    @Query("""
        Select * 
from airport
where iata_code != :name
order by passengers
    """)
    fun getFlightListByAirport(name: String): Flow<List<Airport>>

    @Query("Select * from airport where name like '%' || :name || '%' or iata_code like '%' || :name  || '%' order by passengers")
    fun getIataCodeByName(name: String): Flow<List<Airport>>
}