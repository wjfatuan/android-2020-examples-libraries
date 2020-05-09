package com.example.libraries2.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
class Country(
    @PrimaryKey val code: String,
    var name: String,
    var continent: String
) {
    override fun toString(): String {
        return "{code: $code, name: $name, continent: $continent}"
    }
}