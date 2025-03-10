package com.example.recipeapp.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.recipeapp.R
import com.example.recipeapp.presentation.designs.AppScaffold
import com.example.recipeapp.ui.theme.RecipeAppTheme
import timber.log.Timber


@Composable
fun RecipeScreenRoot(
    navController: NavHostController,
    viewModel: RecipeViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val configuration = rememberUpdatedState(LocalConfiguration.current)
    val isPortrait = remember {
        derivedStateOf { configuration.value.orientation == Configuration.ORIENTATION_PORTRAIT }
    }

    LaunchedEffect(state, isPortrait.value) {
        if (state is RecipeUiState.Success) {
            if (isPortrait.value) {
                //viewModel.setCurrentRecipeIndex(0)
                navController.navigate(Screen.RecipeDetail.route) {
                    popUpTo(Screen.Recipe.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
    AppScaffold(showTopAppBar = false, navController = navController) { paddingValues, snackbarHostState ->
        when (state) {
            is RecipeUiState.Loading -> CircularProgressIndicator()
            is RecipeUiState.Success -> {
                val recipes = (state as RecipeUiState.Success).recipes
                if (!isPortrait.value) {
                    RecipeScreenLandscape(navController, recipes)
                }
            }

            is RecipeUiState.Error -> { //Create a function for this
                val errorMessage = (state as RecipeUiState.Error).message
                LaunchedEffect(errorMessage) {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }
        }
    }
}


@Composable
private fun RecipeScreenLandscape(navController: NavHostController, recipes: List<RecipeUi>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(recipes.size) { index ->
            RecipeCard(recipe = recipes[index]) { recipe ->
                navController.navigate(Screen.RecipeDetail.route)
                {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}

@Composable
fun RecipeCard(modifier: Modifier = Modifier, recipe: RecipeUi, onClick: (recipe: RecipeUi) -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp, vertical = 16.dp)
            .clickable {
                onClick(recipe)
            }
            .semantics { contentDescription = "Recipe: ${recipe.title}, tap to view details" },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    )
    {
        Column(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(recipe.imageUrl)
                        .crossfade(true)
                        .error(R.drawable.ic_error)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .build()
                ), contentDescription = recipe.imageUrlDescription, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = stringResource(R.string.screen_text_recipe),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Text(text = recipe.title, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun RecipeCardPreview() {
    RecipeAppTheme {
        RecipeCard(
            recipe = RecipeUi(
                "title",
                "description",
                "https://coles.com.au/content/dam/coles/inspire-create/thumbnails/Tomato-and-bread-salad-480x288.jpg",
                "imageDescription",
                ingredients = listOf("ingredients")
            )
        )
    }
}

