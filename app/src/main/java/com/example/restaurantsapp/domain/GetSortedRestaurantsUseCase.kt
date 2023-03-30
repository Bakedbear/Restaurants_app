package com.example.restaurantsapp.domain

import com.example.restaurantsapp.data.RestaurantsRepository
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
){

    suspend operator fun invoke(): List<Restaurant>{
        return repository.getRestaurants()
            .sortedBy { it.title }
    }
}