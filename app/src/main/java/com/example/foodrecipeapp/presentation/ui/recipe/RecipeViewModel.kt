package com.example.foodrecipeapp.presentation.ui.recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.example.foodrecipeapp.respository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

@HiltViewModel
class RecipeViewModel
@Inject
constructor(
    private val recipeRepository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(STATE_KEY_RECIPE)?.let { rId ->
            onTriggerEvent(GetRecipeEvent(rId))
        }
    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch { // launch coroutine in viewModelScope
            try {
                when (event) {
                    is GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true
//        simulate network delay for testing
        delay(1000)
        val result = recipeRepository.get(token = token, id = id)
        recipe.value = result
        savedStateHandle[STATE_KEY_RECIPE] = result.id
        loading.value = false
    }

}