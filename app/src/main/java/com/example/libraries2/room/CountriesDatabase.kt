package com.example.libraries2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=[Country::class], version = 1)
abstract class CountriesDatabase : RoomDatabase () {
    abstract fun countriesDao(): CountryDao

    companion object {
        private var instance: CountriesDatabase? = null

        fun getInstance(context: Context) : CountriesDatabase {
            if(instance==null) {
                instance = Room.databaseBuilder(
                    context,
                    CountriesDatabase::class.java, "countriesroom"
                ).build()
            }
            return instance!!
        }
    }
}