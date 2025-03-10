package com.example.recipeapp.presentation.designs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    title: String? = null,
    showTopAppBar: Boolean = true,
    showBackButton: Boolean = false,
    showNextRecipe: Boolean = false,
    onNextRecipeClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues, SnackbarHostState) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            if (showTopAppBar) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title ?: "",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    },
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                    navigationIcon = {
                        if (showBackButton) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    },
                    actions = {
                        if (showNextRecipe && onNextRecipeClick != null) {
                            Text(
                                text = "Next Recipe",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .clickable { onNextRecipeClick() }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ) {
                        Text(
                            text = data.visuals.message,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
        content = { paddingValues ->
            content(paddingValues, snackbarHostState)
        }
    )
}
