package com.example.foodrecipeapp.di

import com.example.foodrecipeapp.cache.RecipeDao
import com.example.foodrecipeapp.cache.model.RecipeEntityMapper
import com.example.foodrecipeapp.interactors.recipe.GetRecipe
import com.example.foodrecipeapp.interactors.recipe_list.RestoreRecipes
import com.example.foodrecipeapp.interactors.recipe_list.SearchRecipes
import com.example.foodrecipeapp.network.model.RecipeDtoMapper
import com.example.foodrecipeapp.network.responses.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun  provideSearchRecipes(
        recipeDao: RecipeDao,
        recipeService: RecipeService,
        recipeEntityMapper: RecipeEntityMapper,
        recipeDtoMapper: RecipeDtoMapper,
    ): SearchRecipes {
        return SearchRecipes(
            recipeDao = recipeDao,
            recipeService = recipeService,
            entityMapper = recipeEntityMapper,
            dtoMapper = recipeDtoMapper
        )
    }
    @ViewModelScoped
    @Provides
    fun  provideRestoreRecipes(
        recipeDao: RecipeDao,
        recipeEntityMapper: RecipeEntityMapper,
    ): RestoreRecipes {
        return RestoreRecipes(
            recipeDao = recipeDao,
            recipeEntityMapper = recipeEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun  provideGetRecipes(
        recipeDao: RecipeDao,
        recipeService: RecipeService,
        recipeEntityMapper: RecipeEntityMapper,
        recipeDtoMapper: RecipeDtoMapper,
    ): GetRecipe {
        return GetRecipe(
            recipeDao = recipeDao,
            recipeService = recipeService,
            recipeEntityMapper = recipeEntityMapper,
            recipeDtoMapper = recipeDtoMapper
        )
    }

}