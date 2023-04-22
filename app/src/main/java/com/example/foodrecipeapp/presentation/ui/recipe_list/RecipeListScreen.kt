package com.example.foodrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.foodrecipeapp.presentation.components.RecipeList
import com.example.foodrecipeapp.presentation.components.SearchAppBar
import com.example.foodrecipeapp.presentation.theme.FoodRecipeAppTheme

@Composable
fun RecipeListScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateToRecipeDetailScreen: (String) -> Unit,
    viewModel: RecipeListViewModel,
) {
    val dialogQueue = viewModel.dialogQueue
    val recipes = viewModel.recipes.value
    val query = viewModel.query.value
    val selectedCategory = viewModel.selectedCategory.value
    val loading = viewModel.loading.value
    val page = viewModel.page.value
    FoodRecipeAppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        dialogQueue = dialogQueue.queue.value
    ) {
        Log.d("RecipeListScreen", "RecipeListScreen: $viewModel")


        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = { viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent) },
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                    onToggleTheme = {
                        onToggleTheme()
                    },
                    isDarktheme = isDarkTheme

                )
            },
            bottomBar = {
                BottomBar()
            },

            ) {
            RecipeList(
                loading = loading,
                page = page,
                recipes = recipes,
                onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                onNextPage = { viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent) },
                paddingValues = it,
                onNavigateToRecipeDetailScreen = onNavigateToRecipeDetailScreen


            )
        }


    }
}

@Composable
fun BottomBar() {
    val selectedItem = remember { mutableStateOf(0) }
    val items = listOf("Songs", "Artists", "Playlists")

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem.value == index,
                onClick = { selectedItem.value = index }
            )
        }
    }
}