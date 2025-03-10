package com.example.recipeapp

import app.cash.turbine.test
import com.example.recipeapp.domain.Recipe
import com.example.recipeapp.domain.RecipeDetails
import com.example.recipeapp.domain.RecipeRepository
import com.example.recipeapp.domain.Result
import com.example.recipeapp.presentation.RecipeIntent
import com.example.recipeapp.presentation.RecipeUiState
import com.example.recipeapp.presentation.RecipeViewModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelTest {

    private lateinit var viewModel: RecipeViewModel
    private val repository: RecipeRepository = mockk()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineRule.testDispatcher)
        coEvery { repository.getRecipes() } returns Result.Success(emptyList())
        viewModel = RecipeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_getRecipes_update_uiState_with_success_on_valid_data() = runTest {

        val fakeRecipeDetails = RecipeDetails(
            amountLabel = "Serves",
            amountNumber = 8,
            prepLabel = "Prep",
            prepTime = "15m",
            prepNote = "+ cooling time",
            cookingLabel = "Cooking",
            cookingTime = "15m",
            cookTimeAsMinutes = 15,
            prepTimeAsMinutes = 15
        )

        val fakeRecipes = listOf(
            Recipe(
                title = "Recipe 1",
                description = "Desc 1",
                imageUrl = "",
                imageDescription = "",
                details = fakeRecipeDetails,
                ingredients = emptyList()
            ),
            Recipe(
                title = "Recipe 2",
                description = "Desc 2",
                imageUrl = "",
                imageDescription = "",
                details = fakeRecipeDetails,
                ingredients = emptyList()
            )
        )
        coEvery { repository.getRecipes() } returns Result.Success(fakeRecipes)


        viewModel.processIntent(RecipeIntent.LoadRecipes)

        advanceUntilIdle()

        viewModel.uiState.test {
            //assert(awaitItem() is RecipeUiState.Loading)
            val successState = awaitItem() as RecipeUiState.Success
            assertEquals(2, successState.recipes.size)
            assertEquals("Recipe 1", successState.recipes[0].title)
            assertEquals("Recipe 2", successState.recipes[1].title)
        }
        coVerify { repository.getRecipes() }

    }

    @Test
    fun test_getRecipes_update_uiState_with_error_when_error() = runTest {

        val errorMessage = "Failed to fetch recipes"
        coEvery { repository.getRecipes() } returns Result.Error(errorMessage)

        viewModel.processIntent(RecipeIntent.LoadRecipes)

        advanceUntilIdle()

        viewModel.uiState.test {
            val errorState = awaitItem() as RecipeUiState.Error
            assertEquals(errorMessage, errorState.message)
        }
        coVerify { repository.getRecipes() }
    }

    @Test
    fun test_nextRecipe_updates_currentRecipeIndex_and_loops_back_to_zero() = runTest {
        val fakeRecipeDetails = RecipeDetails(
            amountLabel = "Serves",
            amountNumber = 8,
            prepLabel = "Prep",
            prepTime = "15m",
            prepNote = "+ cooling time",
            cookingLabel = "Cooking",
            cookingTime = "15m",
            cookTimeAsMinutes = 15,
            prepTimeAsMinutes = 15
        )

        val fakeRecipes = listOf(
            Recipe(
                title = "Recipe 1",
                description = "Desc 1",
                imageUrl = "",
                imageDescription = "",
                details = fakeRecipeDetails,
                ingredients = emptyList()
            ),
            Recipe(
                title = "Recipe 2",
                description = "Desc 2",
                imageUrl = "",
                imageDescription = "",
                details = fakeRecipeDetails,
                ingredients = emptyList()
            )
        )
        coEvery { repository.getRecipes() } returns Result.Success(fakeRecipes)
        viewModel.processIntent(RecipeIntent.LoadRecipes)

        advanceUntilIdle()

        //Initially set to 0
        assertEquals(0, viewModel.currentRecipeIndex.value)
        viewModel.processIntent(RecipeIntent.NextRecipe)

        //Next increment and set to 1
        assertEquals(1, viewModel.currentRecipeIndex.value)

        //Moving to next recipe, loopback again to 0
        viewModel.processIntent(RecipeIntent.NextRecipe)
        assertEquals(0, viewModel.currentRecipeIndex.value)

    }

}
