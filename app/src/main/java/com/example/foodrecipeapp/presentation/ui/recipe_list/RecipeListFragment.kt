package com.example.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.fragment.app.Fragment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.foodrecipeapp.presentation.components.FoodCategoryChip
import com.example.foodrecipeapp.presentation.components.RecipeCard
import com.example.foodrecipeapp.presentation.components.SearchAppBar

class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by hiltNavGraphViewModels(R.id.main_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value

                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val loading = viewModel.loading.value
                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged,
                        onExecuteSearch = viewModel::newSearch,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,

                        )

                   Box(){
                       LazyColumn {
                           itemsIndexed(items = recipes) { index, recipe ->
                               RecipeCard(recipe = recipe, onClick = { })
                           }
                       }
                       CircularIndeterminateProgressBar(isDisplayed=loading)
                   }
                }

            }

        }

    }
}
