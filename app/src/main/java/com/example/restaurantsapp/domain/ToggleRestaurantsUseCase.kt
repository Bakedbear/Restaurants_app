package com.example.restaurantsapp.domain

import com.example.restaurantsapp.data.RestaurantsRepository
import javax.inject.Inject

class ToggleRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {

    suspend operator fun invoke(
        id: Int,
        oldValue: Boolean
    ): List<Restaurant>
    {
        val newFav = oldValue.not()
        repository.toggleFavoriteRestaurant(id, newFav)
        return getSortedRestaurantsUseCase()
    }
}