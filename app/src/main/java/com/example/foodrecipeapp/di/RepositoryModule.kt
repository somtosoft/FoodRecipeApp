package com.example.foodrecipeapp.di

import com.example.foodrecipeapp.network.model.RecipeDtoMapper
import com.example.foodrecipeapp.network.responses.RecipeService
import com.example.foodrecipeapp.respository.RecipeRepository
import com.example.foodrecipeapp.respository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper,
    ): RecipeRepository {
         return RecipeRepository_Impl(recipeService, recipeDtoMapper)
    }
}