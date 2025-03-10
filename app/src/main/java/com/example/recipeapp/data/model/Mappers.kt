package com.example.recipeapp.data.model

import com.example.recipeapp.domain.Recipe
import com.example.recipeapp.domain.RecipeDetails


/**
 * Extension function to convert RecipeDto to Recipe
 */
fun RecipeDto.toRecipe(baseImageUrl: String): Recipe {
    return Recipe(
        title = title,
        description = description ?: "",
        imageUrl = "$baseImageUrl${this.thumbnailUrl}",
        imageDescription = thumbnailAlt ?: "",
        details = details.toRecipeDetails(),
        ingredients = ingredients.map { it.ingredient }
    )
}

fun RecipeDetailsDto.toRecipeDetails(): RecipeDetails {
    return RecipeDetails(
        amountLabel = amountLabel ?: "Serves",
        amountNumber = amountNumber ?: 0,
        prepLabel = prepLabel ?: "Prep",
        prepTime = prepTime ?: "",
        prepNote = prepNote ?: "Note",
        cookingLabel = cookingLabel ?: "Cooking",
        cookingTime = cookingTime ?: "",
        cookTimeAsMinutes = cookTimeMinutes ?: 0,
        prepTimeAsMinutes = prepTimeMinutes ?: 0
    )
}