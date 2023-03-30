package com.example.restaurantsapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.restaurantsapp.domain.DummyContent
import com.example.restaurantsapp.presentation.Description
import com.example.restaurantsapp.presentation.list.RestaurantsScreen
import com.example.restaurantsapp.presentation.list.RestaurantsScreenState
import com.example.restaurantsapp.ui.theme.RestaurantsAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RestaurantsScreenTest {
    @get:Rule
    val testRule: ComposeTestRule = createComposeRule()

    @Test
    fun initialState_isRendered() {
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = emptyList(),
                        isLoading = true
                    ),
                    onFavoriteClick = { _: Int, _: Boolean -> },
                    onItemClick = {})
            }
            testRule.onNodeWithContentDescription(
                Description.RESTAURANTS_LOADING
            ).assertIsDisplayed()
        }
    }

    @Test
    fun stateWithContent_isRendered() {
        val restaurants = DummyContent.getDomainRestaurants()
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = restaurants,
                        isLoading = false
                    ),
                    onFavoriteClick = { _: Int, _: Boolean -> },
                    onItemClick = {})
            }
            testRule.onNodeWithText(restaurants[0].title)
                .assertIsDisplayed()
            testRule.onNodeWithText(restaurants[0].description)
                .assertIsDisplayed()
            testRule.onNodeWithContentDescription(
                Description.RESTAURANTS_LOADING
            ).assertDoesNotExist()

        }
    }

    @Test
    fun errorState_isRendered() {
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = emptyList(),
                        isLoading = false,
                        error = stringResource(R.string.error_msg)
                    ),
                    onFavoriteClick = { _: Int, _: Boolean -> },
                    onItemClick = {}
                )
            }
            testRule.onNodeWithText(
                text = stringResource(R.string.error_msg)
            ).assertIsDisplayed()
            testRule.onNodeWithContentDescription(
                Description.RESTAURANTS_LOADING
            ).assertDoesNotExist()
        }
    }

    @Test
    fun stateWithContent_ClickOnItem_isRegistered() {
        val restaurants = DummyContent.getDomainRestaurants()
        val targetRestaurant = restaurants[0]
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = restaurants,
                        isLoading = false
                    ),
                    onFavoriteClick = { _, _ -> },
                    onItemClick = { id ->
                        assert(id == targetRestaurant.id)
                    })
            }
            testRule.onNodeWithText(
                targetRestaurant.title
            ).performClick()
        }

    }
}

private fun TestRule.setContent(content: @Composable () -> Unit) {

}


