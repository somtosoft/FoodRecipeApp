package com.example.foodrecipeapp.respository

import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.network.model.RecipeDtoMapper

class RecipeRepository_Impl(
    private val recipeService: RecipeRepository,
    private val mapper: RecipeDtoMapper
): RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {

        return mapper.toDomainList(recipeService.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
      return mapper.mapToDomainModel(recipeService.get(token, id))
    }
}