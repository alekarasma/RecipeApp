package com.example.recipeapp.data.model

import com.google.gson.annotations.SerializedName


data class RecipeDto(
    @SerializedName("dynamicTitle")
    val title: String,

    @SerializedName("dynamicDescription")
    val description: String? = null,

    @SerializedName("dynamicThumbnail")
    val thumbnailUrl: String? = null,

    @SerializedName("dynamicThumbnailAlt")
    val thumbnailAlt: String? = null,

    @SerializedName("recipeDetails")
    val details: RecipeDetailsDto,

    @SerializedName("ingredients")
    val ingredients: List<IngredientDto>
)

data class RecipeDetailsDto(
    @SerializedName("amountLabel")
    val amountLabel: String? = null,

    @SerializedName("amountNumber")
    val amountNumber: Int? = null,

    @SerializedName("prepLabel")
    val prepLabel: String? = null,

    @SerializedName("prepTime")
    val prepTime: String? = null,

    @SerializedName("prepNote")
    val prepNote: String? = null,

    @SerializedName("cookingLabel")
    val cookingLabel: String? = null,

    @SerializedName("cookingTime")
    val cookingTime: String? = null,

    @SerializedName("cookTimeAsMinutes")
    val cookTimeMinutes: Int? = null,

    @SerializedName("prepTimeAsMinutes")
    val prepTimeMinutes: Int? = null
)

data class IngredientDto(
    @SerializedName("ingredient")
    val ingredient: String
)

data class RecipeResponse(
    @SerializedName("recipes")
    val recipes: List<RecipeDto>
)

