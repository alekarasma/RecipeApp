package com.example.recipeapp.domain


data class Recipe(
    val title: String,
    val description: String,
    val imageUrl: String,
    val imageDescription: String,
    val details: RecipeDetails,
    val ingredients: List<String>
)

data class RecipeDetails(
    val amountLabel: String,
    val amountNumber: Int,
    val prepLabel: String,
    val prepTime: String,
    val prepNote: String?,
    val cookingLabel: String,
    val cookingTime: String,
    val cookTimeAsMinutes: Int,
    val prepTimeAsMinutes: Int
)
