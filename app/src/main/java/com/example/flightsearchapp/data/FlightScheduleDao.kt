package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Query("select * from favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query(
        """
        SELECT * FROM favorite
        WHERE departure_code = :departureCode
          AND destination_code = :destinationCode
        """
    )
    suspend fun getSingleFavorite(departureCode: String, destinationCode: String): Favorite

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)
}