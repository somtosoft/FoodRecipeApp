package com.example.foodrecipeapp.interactors.recipe

import android.util.Log
import com.example.foodrecipeapp.cache.RecipeDao
import com.example.foodrecipeapp.cache.model.RecipeEntityMapper
import com.example.foodrecipeapp.domain.data.DataState
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.network.model.RecipeDtoMapper
import com.example.foodrecipeapp.network.responses.RecipeService
import com.example.foodrecipeapp.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipe(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService,
    private val recipeEntityMapper: RecipeEntityMapper,
    private val recipeDtoMapper: RecipeDtoMapper
) {
    fun execute(
        recipeId: Int,
        token: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<Recipe>> = flow {
        try {
            emit(DataState.loading<Recipe>())
            delay(1000) // just to show loading state remove it later
            var recipe = getRecipeFromCache(recipeId)
            if(recipe!=null){
                emit(DataState.success(recipe))
            }
            else{
                if(isNetworkAvailable){
                    val networkRecipe = getRecipeFromNetwork(token, recipeId)
                    recipeDao.insertRecipe(recipeEntityMapper.mapFromDomainModel(networkRecipe))
                }

                recipe = getRecipeFromCache(recipeId)
                if(recipe!=null){
                    emit(DataState.success(recipe))
                }
                else{
                    emit(DataState.error<Recipe>("Recipe not found."))
                }
            }

        } catch (e: Exception) {
            Log.d(TAG, "execute: ${e.message}")
            emit(DataState.error<Recipe>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getRecipeFromCache(recipeId: Int): Recipe? {
        return recipeDao.getRecipeById(recipeId)?.let { recipeEntity ->
            recipeEntityMapper.mapToDomainModel(recipeEntity)
        }
    }

    private suspend fun getRecipeFromNetwork(token: String, recipeId: Int): Recipe {
        return recipeDtoMapper.mapToDomainModel(recipeService.get(token, recipeId))
    }

}