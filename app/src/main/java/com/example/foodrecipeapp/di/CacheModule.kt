package com.example.foodrecipeapp.di

import androidx.room.Room
import com.example.foodrecipeapp.cache.RecipeDao
import com.example.foodrecipeapp.presentation.BaseApplication
import com.example.foodrecipeapp.util.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDataBase {
        return Room.databaseBuilder(
            app,
            AppDataBase::class.java,
            AppDataBase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() //use only for testing
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(db: AppDataBase): RecipeDao {
        return db.recipeDao()
    }
}