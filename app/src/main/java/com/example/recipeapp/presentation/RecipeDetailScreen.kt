package com.example.recipeapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.recipeapp.R
import com.example.recipeapp.presentation.designs.AppScaffold
import com.example.recipeapp.presentation.designs.BulletedText
import com.example.recipeapp.presentation.designs.CardLabel
import com.example.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun RecipeDetailScreenRoot(
    navHostController: NavHostController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val currentIndex by viewModel.currentRecipeIndex.collectAsState()

    AppScaffold(
        showTopAppBar = true,
        navController = navHostController,
        showBackButton = true,
        showNextRecipe = true,
        onNextRecipeClick = {
            viewModel.processIntent(RecipeIntent.NextRecipe)
        }
    ) { paddingValues, snackbarHostState ->
        val recipe = viewModel.getRecipeByIndex(currentIndex)

        if (recipe != null) {
            RecipeDetailScreen(
                modifier = Modifier.padding(paddingValues),
                recipe = recipe,
                snackbarHostState = snackbarHostState
            )
        } else {
            LaunchedEffect(true) {
                snackbarHostState.showSnackbar("Recipes not available")
            }
        }
    }
}

@Composable
private fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    recipe: RecipeUi,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(recipe.imageUrl)
            .crossfade(true)
            .error(R.drawable.ic_error)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .build()
    )

    LaunchedEffect(painter.state) {
        if (painter.state is AsyncImagePainter.State.Error) {
            snackbarHostState.showSnackbar("Failed to load image")
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            recipe.title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            recipe.description.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))


        Image(
            painter = painter,
            contentDescription = recipe.imageUrlDescription,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
                .semantics { contentDescription = "Recipe image for ${recipe.title}" },
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(MaterialTheme.colorScheme.surface)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardLabel(modifier, recipe.servesLabel ?: "Serves", recipe.noOfServes ?: "")
            CardLabel(modifier, recipe.prepTimeLabel ?: "Prep", recipe.prepTimeString ?: "")
            CardLabel(modifier, recipe.cookingTimeLabel ?: "Cooking", recipe.cookingTimeString ?: "")
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (recipe.ingredients.isNotEmpty()) {
            Text(
                text = "Ingredients",
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            recipe.ingredients.forEach { ingredient ->
                BulletedText(text = ingredient)
            }
        }
    }
}

@Preview
@Composable
fun RecipeDetailView() {
    RecipeAppTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        RecipeDetailScreen(
            recipe = RecipeUi(
                "title",
                "description",
                "https://coles.com.au/content/dam/coles/inspire-create/thumbnails/Tomato-and-bread-salad-480x288.jpg",
                "imageDescription",
                "Serves",
                "8",
                "Prep",
                "15m",
                "Cooking",
                "4h30m",
                ingredients = listOf("Tomato", "Bread", "Spinach")
            ),
            snackbarHostState = snackbarHostState
        )
    }
}