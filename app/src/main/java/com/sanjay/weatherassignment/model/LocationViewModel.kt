package com.sanjay.weatherassignment.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sanjay.weatherassignment.db.LocationDatabase
import com.sanjay.weatherassignment.repositry.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LocationRepository
    val allLocations: LiveData<List<Location>>

    init {
        val locationDao = LocationDatabase.getDatabase(application, viewModelScope).locationDao()
        repository = LocationRepository(locationDao)
        allLocations = repository.allLocations
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(location: Location) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(location)
    }

    /**
     * Launching a new coroutine to delete the data in a non-blocking way
     */
    fun delete(location: Location) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(location)
    }
}