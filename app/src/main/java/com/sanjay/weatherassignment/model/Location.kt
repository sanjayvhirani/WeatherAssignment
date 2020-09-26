package com.sanjay.weatherassignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class Location(
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "temperature") val temperature: String,
    @ColumnInfo(name = "speed") val speed: String,
    @ColumnInfo(name = "icon") val icon: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}