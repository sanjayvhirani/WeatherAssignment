package com.sanjay.weatherassignment.repositry

import androidx.lifecycle.LiveData
import com.sanjay.weatherassignment.db.LocationDao
import com.sanjay.weatherassignment.model.Location

class LocationRepository(private val locationDao: LocationDao) {

    val allLocations: LiveData<List<Location>> = locationDao.fetchAllLocations()

    suspend fun insert(location: Location) {
        locationDao.insert(location)
    }

    suspend fun delete(location: Location) {
        locationDao.deleteLocation(location)
    }
}