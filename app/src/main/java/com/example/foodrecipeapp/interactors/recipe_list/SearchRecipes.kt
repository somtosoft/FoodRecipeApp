package com.example.foodrecipeapp.interactors.recipe_list

import com.example.foodrecipeapp.cache.RecipeDao
import com.example.foodrecipeapp.cache.model.RecipeEntityMapper
import com.example.foodrecipeapp.domain.data.DataState
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.network.model.RecipeDtoMapper
import com.example.foodrecipeapp.network.responses.RecipeService
import com.example.foodrecipeapp.util.RECIPE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipes(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService,
    private val entityMapper: RecipeEntityMapper,
    private val dtoMapper: RecipeDtoMapper,
) {
    fun execute(
        token: String,
        page: Int,
        query: String,
        isNetworkAvailable:Boolean,
    ): Flow<DataState<List<Recipe>>> = flow {

        try {
            emit(DataState.loading<List<Recipe>>())
            delay(1000) //remove in production, just to show pagination loading

//            force error for testing
            if (query == "error") {
                throw Exception("Something went wrong.")
            }

            if(isNetworkAvailable){
                val recipes = getRecipesFromNetwork(token, page, query)

//            insert into cache
                recipeDao.insertRecipes(entityMapper.toEntityList(recipes))
            }
//            query the cache
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

//            emit a list<Recipe> from the cache
            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success<List<Recipe>>(list))


        } catch (e: Exception) {
            emit(DataState.error<List<Recipe>>(e.message ?: "Unknown Error"))
        }
    }

    //    this can throw an exception if no network connection
    private suspend fun getRecipesFromNetwork(
        token: String,
        page: Int,
        query: String,
    ): List<Recipe> {
        //        for(recipe in recipes){
//            recipeDao.insert(entityMapper.mapFromDomainModel(recipe))
//        }
        return dtoMapper.toDomainList(
            recipeService.search(
                token = token,
                page = page,
                query = query
            ).recipes
        )
    }

}