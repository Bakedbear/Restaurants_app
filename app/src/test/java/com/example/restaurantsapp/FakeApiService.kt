package com.example.restaurantsapp

import com.example.restaurantsapp.data.remote.RemoteRestaurant
import com.example.restaurantsapp.data.remote.RestaurantsApiService
import com.example.restaurantsapp.domain.DummyContent
import kotlinx.coroutines.delay

class FakeApiService: RestaurantsApiService {
    override suspend fun getRestaurants(): List<RemoteRestaurant>{

        delay(1000)
        return DummyContent.getRemoteRestaurants()
    }

    override suspend fun getRestaurant(id: Int): Map<String, RemoteRestaurant> {
        TODO("Not yet implemented")
    }

}