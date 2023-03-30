package com.example.restaurantsapp

import com.example.restaurantsapp.data.RestaurantsRepository
import com.example.restaurantsapp.domain.DummyContent
import com.example.restaurantsapp.domain.GetSortedRestaurantsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSortedRestaurantsUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun getSortedRestaurants_isReturningSortedRestaurants() = scope.runTest{

        val restaurantsRepository = RestaurantsRepository(
            FakeApiService(),
            FakeRoomDao(),
            dispatcher
        )

        val useCase = GetSortedRestaurantsUseCase(restaurantsRepository)

        restaurantsRepository.loadRestaurants()
        advanceUntilIdle()

        val restaurants = DummyContent.getDomainRestaurants()
        val sortedRestaurants = useCase().sortedBy { it.title }
        advanceUntilIdle()

        assert(restaurants != sortedRestaurants)

    }
}