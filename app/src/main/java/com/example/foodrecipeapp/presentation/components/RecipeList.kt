package com.example.foodrecipeapp.presentation.components

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.presentation.ui.recipe_list.PAGE_SIZE
import com.example.foodrecipeapp.presentation.ui.recipe_list.RecipeListEvent

@Composable
fun RecipeList(
    loading: Boolean,
    page: Int,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    onNextPage:(RecipeListEvent) -> Unit,
    paddingValues: PaddingValues,
    navController: NavController

) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(paddingValues)
    ) {
        LazyColumn(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {

            itemsIndexed(items = recipes) { index, recipe ->
                onChangeRecipeScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                    onNextPage(RecipeListEvent.NextPageEvent)
                }
                RecipeCard(recipe = recipe,
                    onClick = {
                    if(recipe.id != null) {
                        val bundle = Bundle()
                        bundle.putInt("recipeId", recipe.id)
                        navController.navigate(R.id.viewRecipe, bundle)
                    }else{
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
                )
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)

    }
}