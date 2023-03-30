package com.example.restaurantsapp

import com.example.restaurantsapp.data.RestaurantsRepository
import com.example.restaurantsapp.domain.DummyContent
import com.example.restaurantsapp.domain.GetInitialRestaurantsUseCase
import com.example.restaurantsapp.domain.GetSortedRestaurantsUseCase
import com.example.restaurantsapp.domain.ToggleRestaurantsUseCase
import com.example.restaurantsapp.presentation.list.RestaurantsScreenState
import com.example.restaurantsapp.presentation.list.RestaurantsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class RestaurantsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun initialState_isProduced() = scope.runTest{
        val viewModel = getViewModel()
        val initialState = viewModel.state.value
        assert(
            initialState == RestaurantsScreenState(
                restaurants = emptyList(),
                isLoading = true,
                error = null
            )
        )

    }

    @Test
    fun stateWithContent_isProduced() = scope.runTest {
        val testVM = getViewModel()
        advanceUntilIdle()
        val currentState = testVM.state.value
        assert(
            currentState == RestaurantsScreenState(
                restaurants = DummyContent.getDomainRestaurants(),
                isLoading = false,
                error = null
            )
        )
    }

    private fun getViewModel(): RestaurantsViewModel{
        val restaurantsRepository = RestaurantsRepository(FakeApiService(), FakeRoomDao(), dispatcher)
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(
            restaurantsRepository)
        val getInitialRestaurantsUseCase = GetInitialRestaurantsUseCase(restaurantsRepository,
        getSortedRestaurantsUseCase)
        val toggleRestaurantsUseCase = ToggleRestaurantsUseCase(
            restaurantsRepository, getSortedRestaurantsUseCase)


        return RestaurantsViewModel(
            getInitialRestaurantsUseCase, toggleRestaurantsUseCase, dispatcher
        )
    }
}