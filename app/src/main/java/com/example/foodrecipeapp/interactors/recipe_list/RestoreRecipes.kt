package com.example.foodrecipeapp.interactors.recipe_list

import com.example.foodrecipeapp.cache.RecipeDao
import com.example.foodrecipeapp.cache.model.RecipeEntityMapper
import com.example.foodrecipeapp.domain.data.DataState
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.util.RECIPE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestoreRecipes(
    private val recipeDao: RecipeDao,
    private val recipeEntityMapper: RecipeEntityMapper,
) {

    fun execute(
        query: String,
        page: Int,
    ): Flow<DataState<List<Recipe>>> = flow {
        try {
            emit(DataState.loading())
            val cacheResult = if (query.isBlank()) {
                recipeDao.getAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            } else {
                recipeDao.searchRecipes(
                    query = query, pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }
            val list = recipeEntityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))
        } catch (e: Exception) {
            emit(DataState.error<List<Recipe>>(e.message ?: "Unknown Error"))
        }
    }
}