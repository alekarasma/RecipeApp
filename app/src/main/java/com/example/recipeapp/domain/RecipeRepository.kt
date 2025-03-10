package com.example.recipeapp.domain

interface RecipeRepository {
    suspend fun getRecipes(): Result<List<Recipe>>
}