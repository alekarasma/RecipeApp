package com.example.recipeapp.presentation

sealed class RecipeIntent {
    data object LoadRecipes : RecipeIntent()
    data object NextRecipe : RecipeIntent()
}
