package com.example.recipeapp.presentation.util

import com.example.recipeapp.domain.Recipe
import com.example.recipeapp.presentation.RecipeUi

/**
 * Extension function to convert a Recipe object to a RecipeUi object.
 */
fun Recipe.toRecipeUi(): RecipeUi {
    return RecipeUi(
        title = title,
        description = description,
        imageUrl = imageUrl,
        imageUrlDescription = imageDescription,
        servesLabel = details.amountLabel,
        noOfServes = if (details.amountNumber == 0) "" else details.amountNumber.toString(),
        prepTimeLabel = details.prepLabel,
        prepTimeString = details.prepTime,
        cookingTimeLabel = details.cookingLabel,
        cookingTimeString = details.cookingTime,
        ingredients = ingredients
    )

}