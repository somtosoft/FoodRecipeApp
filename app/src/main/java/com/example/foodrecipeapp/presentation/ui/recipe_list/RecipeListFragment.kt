package com.example.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.presentation.BaseApplication
import com.example.foodrecipeapp.presentation.components.*
import com.example.foodrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.example.foodrecipeapp.ui.theme.FoodRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by hiltNavGraphViewModels(R.id.main_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                FoodRecipeAppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val loading = viewModel.loading.value
                    val page = viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = { viewModel.onTriggerEvent(NewSearchEvent) },
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = {
                                    application.toggleLightTheme()
                                },
                                isDarktheme = application.isDark.value

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
                           onNextPage = {viewModel.onTriggerEvent(NextPageEvent)},
                           paddingValues = it,
                           navController = findNavController()
                       )
                    }


                }

            }

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
