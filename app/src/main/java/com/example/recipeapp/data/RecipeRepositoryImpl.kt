package com.example.recipeapp.data

import android.content.Context
import com.example.recipeapp.R
import com.example.recipeapp.data.model.RecipeResponse
import com.example.recipeapp.data.model.toRecipe
import com.example.recipeapp.domain.Recipe
import com.example.recipeapp.domain.RecipeRepository
import com.example.recipeapp.domain.Result
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class RecipeRepositoryImpl @Inject constructor(
    private val context: Context,
    @Named("BaseImageUrl") private val baseImageUrl: String
) : RecipeRepository {

    override suspend fun getRecipes(): Result<List<Recipe>> = withContext(Dispatchers.IO) {
        try {
            Timber.d("getRecipes")

            val inputStream = context.resources.openRawResource(R.raw.recipes_sample)

            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val recipeResponse = Gson().fromJson(jsonString, RecipeResponse::class.java)

            val recipeList = recipeResponse.recipes.map { it.toRecipe(baseImageUrl) }

            return@withContext Result.Success(recipeList)
        } catch (e: Exception) {

            Timber.e("Error getting recipes ${e.message}")

            return@withContext Result.Error("Oops! Something went wrong. Please try again later.")
        }
    }
}