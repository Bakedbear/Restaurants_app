package com.example.restaurantsapp.domain

import com.example.restaurantsapp.data.remote.RemoteRestaurant

object DummyContent {
    fun getDomainRestaurants() = arrayListOf(
        Restaurant(0, "Mickey's Fish", "description0", false),
        Restaurant(1, "Donald Duck Restaurant", "description1", false),
        Restaurant(2, "The Mojos", "description2", false),
        Restaurant(3, "Restaurant Chicken", "description3", false)

    )
    fun getRemoteRestaurants() = getDomainRestaurants()
        .map {
            RemoteRestaurant(
                it.id,
                it.title,
                it.description
            )
        }
}