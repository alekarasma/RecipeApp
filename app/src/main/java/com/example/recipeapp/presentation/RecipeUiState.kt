package com.example.recipeapp.presentation

import com.example.recipeapp.domain.Recipe

sealed class RecipeUiState {
    data object Loading : RecipeUiState()
    data class Success(
        val recipes: List<RecipeUi>,
        val currentRecipeIndex: Int = 0,
        val currentRecipeUi: RecipeUi? = null
    ) : RecipeUiState()

    data class Error(val message: String) : RecipeUiState()
}


data class RecipeUi(
    val title: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val imageUrlDescription: String? = null,
    val servesLabel:String?= null,
    val noOfServes: String? = null,
    val prepTimeLabel: String? = null,
    val prepTimeString: String? = null,
    val cookingTimeLabel: String? = null,
    val cookingTimeString: String? = null,
    val ingredients: List<String> = emptyList()
)