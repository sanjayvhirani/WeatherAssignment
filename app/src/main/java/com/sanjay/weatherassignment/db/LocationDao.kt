package com.sanjay.weatherassignment.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sanjay.weatherassignment.model.Location

@Dao
interface LocationDao {

    @Insert
    suspend fun insert(location: Location)

    @Query("SELECT * FROM location_table ORDER BY id ASC")
    fun fetchAllLocations(): LiveData<List<Location>>

    @Query("SELECT * FROM location_table WHERE id =:locationId")
    fun getLocation(locationId: Int): LiveData<Location>

    @Delete
    suspend fun deleteLocation(location: Location)

    @Update
    suspend fun update(location: Location): Int
}