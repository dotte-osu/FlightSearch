package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the FlightSchedule database
 */
@Dao
interface FlightScheduleDao {

//    @Query("Select * from airport")
//    fun getAll(): Flow<List<Airport>>
//
    @Query("Select * from airport where name = :name order by name")
    fun getAirportByName(name: String): Flow<List<Airport>>

    @Query("Select * from airport where name like '%' || :input || '%' or iata_code like '%' || :input  || '%' order by passengers")
    fun getIataCodeByName(input: String): Flow<List<Airport>>
}