package com.example.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.fragment.app.Fragment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.presentation.components.FoodCategoryChip
import com.example.foodrecipeapp.presentation.components.RecipeCard
import com.example.foodrecipeapp.util.TAG

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
                Column {
                    Surface(
                        shadowElevation = 8.dp,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,

                        ) {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(8.dp),
                                    value = query,
                                    onValueChange = { newValue ->
                                        viewModel.onQueryChanged(newValue)
                                    },
                                    label = {
                                        Text(text = "Search For Recipes")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Search
                                    ),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = "Search Icon",
                                            modifier = Modifier.size(18.dp)
                                        )
                                    },
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            viewModel.newSearch()
                                            defaultKeyboardAction(ImeAction.Done)
                                        },
                                    )
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .horizontalScroll(rememberScrollState())
                                    .fillMaxWidth()
                            ) {
                                for (category in getAllFoodCategories()) {
                                    FoodCategoryChip(
                                        category = category.value,
                                        isSelected = selectedCategory == category,
                                        onSelectedCategoryChanged = {
                                            viewModel.onSelectedCategoryChanged(it)
                                        },

                                        onExecuteSearch =
                                        viewModel::newSearch
                                    )

                                }
                            }
                        }
                    }
                    LazyColumn {
                        itemsIndexed(items = recipes) { index, recipe ->
                            RecipeCard(recipe = recipe, onClick = { })
                        }
                    }
                }

            }

        }

    }
}
