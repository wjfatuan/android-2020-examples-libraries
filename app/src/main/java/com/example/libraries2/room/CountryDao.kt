package com.example.libraries2.room

import androidx.room.*

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries WHERE code = :pcode")
    fun findByCode(pcode: String) : Country

    @Query("SELECT * FROM countries")
    fun buscarTodos(): List<Country>

    @Insert
    fun insert(country: Country)

    @Update
    fun update(country: Country)

    @Delete
    fun delete(country: Country)

}