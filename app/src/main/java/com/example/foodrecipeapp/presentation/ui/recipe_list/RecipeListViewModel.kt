package com.example.foodrecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.foodrecipeapp.interactors.recipe_list.SearchRecipes
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.interactors.recipe_list.RestoreRecipes
import com.example.foodrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.example.foodrecipeapp.presentation.ui.util.DialogQueue
import com.example.foodrecipeapp.presentation.util.InternetConnectivityManager
import com.example.foodrecipeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30
const val STATE_KEY_PAGE = "state_key_page"
const val STATE_KEY_QUERY = "state_key_query"
const val STATE_KEY_LIST_POSITION = "state_key_list_position"
const val STATE_KEY_SELECTED_CATEGORY = "state_key_selected_category"

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val searchRecipes: SearchRecipes,
    private val restoreRecipes: RestoreRecipes,
    private val internetConnectivityManager: InternetConnectivityManager,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0
    val dialogQueue = DialogQueue()

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }
        if (recipeListScrollPosition != 0) {
            onTriggerEvent(RestoreStateEvent)
        } else {
            onTriggerEvent(NewSearchEvent)
        }
    }


    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                    is RestoreStateEvent -> {
                        restoreState()
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun restoreState() {
        restoreRecipes.execute(page = page.value, query = query.value)
            .onEach { dataState ->
                loading.value = dataState.loading
                dataState.data?.let { list ->
                    recipes.value = list
                }
                dataState.error?.let { error ->
                    Log.e(TAG, "restoreState: $error")
                    dialogQueue.appendErrorMessage(
                        "Error",
                        error
                    )

                }
            }.launchIn(viewModelScope)
    }

    private fun newSearch() {
        Log.d(TAG, "newSearch: query: ${query.value}, page: ${page.value}")
        resetSearchState()
        searchRecipes.execute(token = token, page = page.value, query = query.value, internetConnectivityManager.isNetworkAvailable.value)
            .onEach { dataState ->
                loading.value = dataState.loading
                dataState.data?.let { list ->
                    recipes.value = list
                }
                dataState.error?.let { error ->
                    Log.e(TAG, "newSearch: $error")
                    dialogQueue.appendErrorMessage(
                        "Error",
                        error
                    )
                }
            }.launchIn(viewModelScope)


    }

    private fun nextPage() {

//            prevent duplicate events due to recompose happening to quickly
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            increasePage()
            if (page.value > 1) {
                loading.value = true
                searchRecipes.execute(token = token, page = page.value, query = query.value,internetConnectivityManager.isNetworkAvailable.value)
                    .onEach { dataState ->
                        loading.value = dataState.loading
                        dataState.data?.let { list ->
//                    appendRecipes(recipes)
                            appendRecipes(list)
                        }
                        dataState.error?.let { error ->
                            Log.e(TAG, "nextPage: $error")
//                            TODO("Handle Error")
                        }
                    }.launchIn(viewModelScope)
            }
        }

    }

    private fun resetSearchState() {
        recipes.value = listOf()
        setPage(1)
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    //    Append new recipes to the current list of recipes
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun increasePage() {
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position)
    }

    //    set instance state functions
    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle[STATE_KEY_LIST_POSITION] = position
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle[STATE_KEY_PAGE] = page
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle[STATE_KEY_SELECTED_CATEGORY] = category
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }

}