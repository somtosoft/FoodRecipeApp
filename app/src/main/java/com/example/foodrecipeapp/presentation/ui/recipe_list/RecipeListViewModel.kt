package com.example.foodrecipeapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.example.foodrecipeapp.respository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
const val PAGE_SIZE= 30
const val STATE_KEY_PAGE = "state_key_page"
const val STATE_KEY_QUERY = "state_key_query"
const val STATE_KEY_LIST_POSITION = "state_key_list_position"
const val STATE_KEY_SELECTED_CATEGORY = "state_key_selected_category"

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val randomString: String,
    private val recipeRepository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

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
       if(recipeListScrollPosition != 0){
           onTriggerEvent(RestoreStateEvent)
         }else{
           onTriggerEvent(NewSearchEvent)
       }
    }


fun onTriggerEvent(event: RecipeListEvent){
    viewModelScope.launch {
        try {
            when(event){
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
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}
    private suspend fun restoreState(){
        loading.value = true
        // Must retrieve each page of results. TODO replace with cache later.
        val results: MutableList<Recipe> = mutableListOf()
        for(p in 1..page.value){
            val result = recipeRepository.search(
                token = token,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if(p == page.value){
                results.removeAt(results.size - 1)
            }
        }
        recipes.value = results
        loading.value = false
    }
    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        delay(2000)
            val result = recipeRepository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result
            loading.value = false

    }

    private suspend fun nextPage(){

//            prevent duplicate events due to recompose happening to quickly
            if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)){
                loading.value = true
                increasePage()
                delay(2000)
              if(page.value > 1){
                  val result = recipeRepository.search(
                      token = token,
                      page = page.value,
                      query = query.value
                  )
                  appendRecipes(result)
                }
                loading.value = false
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
    private fun setListScrollPosition(position: Int){
        recipeListScrollPosition = position
    savedStateHandle[STATE_KEY_LIST_POSITION] = position
    }
    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle[STATE_KEY_PAGE] = page
    }
    private fun setSelectedCategory(category: FoodCategory?){
        selectedCategory.value = category
        savedStateHandle[STATE_KEY_SELECTED_CATEGORY] = category
    }
    private fun setQuery(query: String){
    this.query.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }

}