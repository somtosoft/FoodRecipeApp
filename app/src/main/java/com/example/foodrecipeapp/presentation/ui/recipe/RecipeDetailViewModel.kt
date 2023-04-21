package com.example.foodrecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.interactors.recipe.GetRecipe
import com.example.foodrecipeapp.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.example.foodrecipeapp.presentation.ui.util.DialogQueue
import com.example.foodrecipeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

@HiltViewModel
class RecipeDetailViewModel
@Inject
constructor(
    private val getRecipe: GetRecipe,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val onLoad: MutableState<Boolean> = mutableStateOf(false)
    val dialogQueue = DialogQueue()

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

    private fun getRecipe(id: Int) {
        getRecipe.execute(id, token).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { data ->
                recipe.value = data
                savedStateHandle[STATE_KEY_RECIPE] = data.id
            }
            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("Error", error)
            }
        }.launchIn(viewModelScope)
    }

}