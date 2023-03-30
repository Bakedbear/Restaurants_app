package com.example.restaurantsapp.data.local

import androidx.room.*
import com.example.restaurantsapp.data.local.LocalRestaurant
import com.example.restaurantsapp.data.local.PartialLocalRestaurant

@Dao
interface RestaurantsDao {

    //returns all restaurants previously cached within the database
    @Query("SELECT * FROM restaurants")
    suspend fun getAll(): List<LocalRestaurant>

    //caches received restaurants inside the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurants: List<LocalRestaurant>)

    @Update(entity = LocalRestaurant::class)
    suspend fun update(partialRestaurant: PartialLocalRestaurant)

    @Update(entity = LocalRestaurant::class)
    suspend fun updateAll(partialRestaurants: List<PartialLocalRestaurant>)

    @Query("SELECT * FROM restaurants WHERE is_favorite = 1")
    suspend fun getAllFavorited(): List<LocalRestaurant>

}