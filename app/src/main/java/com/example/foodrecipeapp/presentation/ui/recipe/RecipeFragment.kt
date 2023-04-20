package com.example.foodrecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.presentation.BaseApplication
import com.example.foodrecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.foodrecipeapp.presentation.components.RecipeCard
import com.example.foodrecipeapp.presentation.components.RecipeView
import com.example.foodrecipeapp.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.example.foodrecipeapp.ui.theme.FoodRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getInt("recipeId")?.let { rId ->
            viewModel.onTriggerEvent(GetRecipeEvent(rId))

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                FoodRecipeAppTheme(darkTheme = application.isDark.value) {
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
    }
}







