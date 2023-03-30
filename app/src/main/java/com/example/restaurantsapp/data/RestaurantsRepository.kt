package com.example.restaurantsapp.data

import com.example.restaurantsapp.RestaurantsApplication
import com.example.restaurantsapp.data.di.IoDispatcher
import com.example.restaurantsapp.data.local.RestaurantsDb
import com.example.restaurantsapp.data.local.LocalRestaurant
import com.example.restaurantsapp.data.local.PartialLocalRestaurant
import com.example.restaurantsapp.data.local.RestaurantsDao
import com.example.restaurantsapp.data.remote.RestaurantsApiService
import com.example.restaurantsapp.domain.Restaurant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepository @Inject constructor(
    private val restInterface: RestaurantsApiService,
    private val restaurantsDao: RestaurantsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun toggleFavoriteRestaurant(id:Int, value: Boolean) =
        withContext(dispatcher){
            restaurantsDao.update(
                PartialLocalRestaurant(
                    id = id,
                    isFavorite = value
                )
            )
        }

    suspend fun loadRestaurants(){

        return withContext(dispatcher) {
            try {
                refreshCache()

            }catch (e: Exception){
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty())
                            throw Exception(
                                "Something went wrong. " +
                                        "We have no data.")

                    }
                    else -> throw e
                }
            }
        }
    }

    suspend fun getRestaurants(): List<Restaurant> {
        return withContext(dispatcher){
            return@withContext restaurantsDao.getAll().map{
                Restaurant(it.id, it.title, it.description, it.isFavorite)
            }
        }

    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favoriteRestaurants = restaurantsDao.getAllFavorited()
        restaurantsDao.addAll(remoteRestaurants.map{
            LocalRestaurant(
                it.id,
                it.title,
                it.description,
                false
            )
        })
        restaurantsDao.updateAll(
            favoriteRestaurants.map {

                PartialLocalRestaurant(it.id, true)

            })

    }

}