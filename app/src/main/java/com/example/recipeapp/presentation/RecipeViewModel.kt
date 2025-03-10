package com.example.recipeapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.RecipeRepository
import com.example.recipeapp.domain.Result
import com.example.recipeapp.presentation.util.toRecipeUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor( private val state: SavedStateHandle, private val repository: RecipeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val uiState: StateFlow<RecipeUiState> get() = _uiState

    private val _currentRecipeIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentRecipeIndex: StateFlow<Int> get() = _currentRecipeIndex

    init {
        Timber.d("Initializing RecipeViewModel")
        getRecipes()
    }

//    fun setCurrentRecipeIndex(index: Int) {
//        Timber.d("Setting current recipe index to $index")
//        val currentState = _uiState.value
//        if (currentState is RecipeUiState.Success) {
//            _currentRecipeIndex.value = index
//        } else {
//            _currentRecipeIndex.value = 0
//        }
//    }

    fun getRecipeByIndex(index: Int): RecipeUi? {
        val currentState = _uiState.value
        return if (currentState is RecipeUiState.Success) {
            if (index in currentState.recipes.indices) {
                currentState.recipes[index]
            } else {
                null
            }
        } else {
            null
        }
    }

    /**
     * Fetch recipes from the repository and update the UI state accordingly.
     */
    private fun getRecipes() {
        viewModelScope.launch {
            Timber.d("Fetching recipes Loading State")
            _uiState.value = RecipeUiState.Loading
            when (val result = repository.getRecipes()) {
                is Result.Error -> {
                    Timber.d("Fetching recipes Error State")
                    _uiState.value = RecipeUiState.Error(result.errMessage)
                }

                is Result.Success -> {
                    Timber.d("Fetching recipes Success State")
                    val data = result.data.map { it.toRecipeUi() }
                    _uiState.value = RecipeUiState.Success(
                        recipes = data
                    )
                }
            }
        }
    }

    fun processIntent(intent: RecipeIntent) {
        val currentState = _uiState.value
        when (intent) {
            is RecipeIntent.LoadRecipes -> getRecipes()

            is RecipeIntent.NextRecipe -> {
                if (currentState is RecipeUiState.Success) {
                    _currentRecipeIndex.value = (currentRecipeIndex.value + 1) % currentState.recipes.size
                }
            }
        }
    }
}

