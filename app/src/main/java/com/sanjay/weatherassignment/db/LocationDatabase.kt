package com.sanjay.weatherassignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sanjay.weatherassignment.model.Location
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = arrayOf(Location::class),
    version = 1,
    exportSchema = false
)
public abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LocationDatabase {
            val dbInstance = INSTANCE
            if (dbInstance != null) {
                return dbInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}