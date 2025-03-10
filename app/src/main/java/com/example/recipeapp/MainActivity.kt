package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeapp.presentation.HomeScreen
import com.example.recipeapp.presentation.RecipeDetailScreenRoot
import com.example.recipeapp.presentation.RecipeScreenRoot
import com.example.recipeapp.presentation.RecipeUi
import com.example.recipeapp.presentation.RecipeViewModel
import com.example.recipeapp.presentation.Screen
import com.example.recipeapp.ui.theme.RecipeAppTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RecipeAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel: RecipeViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.Recipe.route) {
            RecipeScreenRoot(navController, viewModel)
        }

        composable(Screen.RecipeDetail.route) {
            RecipeDetailScreenRoot(navController, viewModel)
        }
    }
}
