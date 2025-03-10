package com.example.recipeapp.presentation

sealed class Screen(val route: String) {
    data object Home : Screen("homeScreen")
    data object Recipe : Screen("recipeScreen")
    data object RecipeDetail : Screen("recipeDetailScreen")
}