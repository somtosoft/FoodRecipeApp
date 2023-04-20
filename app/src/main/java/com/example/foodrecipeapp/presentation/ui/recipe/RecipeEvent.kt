package com.example.foodrecipeapp.presentation.ui.recipe

sealed class RecipeEvent{
    data class GetRecipeEvent(val id: Int): RecipeEvent()
//    data class GetNextRecipeEvent(val id: Int): RecipeEvent()
//    data class GetPreviousRecipeEvent(val id: Int): RecipeEvent()
//    object None: RecipeEvent()
}
