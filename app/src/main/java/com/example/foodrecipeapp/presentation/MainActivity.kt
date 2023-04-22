package com.example.foodrecipeapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodrecipeapp.datastore.SettingsDataStore
import com.example.foodrecipeapp.presentation.navigation.Screen
import com.example.foodrecipeapp.presentation.ui.recipe.RecipeDetailScreen
import com.example.foodrecipeapp.presentation.ui.recipe.RecipeDetailViewModel
import com.example.foodrecipeapp.presentation.ui.recipe_list.RecipeListScreen
import com.example.foodrecipeapp.presentation.ui.recipe_list.RecipeListViewModel
import com.example.foodrecipeapp.presentation.util.InternetConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
@Inject
lateinit var internetConnectivityManager: InternetConnectivityManager
override fun onStart() {
    super.onStart()
    internetConnectivityManager.registerConnectionObserver(this)

}

    override fun onDestroy() {
        super.onDestroy()
        internetConnectivityManager.unregisterConnectionObserver(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val store = SettingsDataStore(LocalContext.current)
            val isDark = store.getDarkThemeMode.collectAsState(initial =false)
            NavHost(navController = navController, startDestination = Screen.RecipeList.route,){
                composable(route = Screen.RecipeList.route){navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: RecipeListViewModel = viewModel(key = "RecipeListViewModel", factory = factory)
                    RecipeListScreen(
                        isDarkTheme = isDark.value,
                        isNetworkAvailable=internetConnectivityManager.isNetworkAvailable.value,
                        onToggleTheme = {
                            CoroutineScope(Dispatchers.IO).launch {
                                store.changeDarkThemeMode(!isDark.value)
                            }
                        },
                        onNavigateToRecipeDetailScreen = navController::navigate,
                        viewModel = viewModel)
                }
                composable(route = Screen.RecipeDetail.route+"/{recipeId}",
                arguments= listOf(navArgument("recipeId"){
                    type = NavType.IntType
                })
                ){navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: RecipeDetailViewModel = viewModel(key = "RecipeViewModel", factory = factory)
                    RecipeDetailScreen(
                        isDarkTheme = isDark.value,
                        isNetworkAvailable=internetConnectivityManager.isNetworkAvailable.value,
                        recipeId=navBackStackEntry.arguments?.getInt("recipeId")!!,

                        viewModel = viewModel
                    )
                }
            }

        }
    }
}
