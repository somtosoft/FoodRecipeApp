package com.example.foodrecipeapp.presentation.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodrecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.foodrecipeapp.presentation.components.RecipeView
import com.example.foodrecipeapp.presentation.theme.FoodRecipeAppTheme

@Composable
fun RecipeDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable:Boolean,
    recipeId: Int,
    viewModel: RecipeDetailViewModel,
) {

    if (recipeId == null) {
        Text(text = "Error retrieving recipe")
    } else {
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
        val loading = viewModel.loading.value
        val recipe = viewModel.recipe.value
        val dialogQueue = viewModel.dialogQueue
        FoodRecipeAppTheme(darkTheme = isDarkTheme, isNetworkAvailable=isNetworkAvailable,dialogQueue = dialogQueue.queue.value) {
            Scaffold {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.surface)
                ) {
                    if (loading && recipe == null) {

                        Text(text = "Loading...")
                    } else {
                        recipe?.let { it ->
                            RecipeView(recipe = it)

                        }
                    }
                    CircularIndeterminateProgressBar(isDisplayed = loading)
                }
            }

        }
    }

}