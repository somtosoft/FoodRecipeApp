package com.example.foodrecipeapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodrecipeapp.cache.RecipeDao
import com.example.foodrecipeapp.cache.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class AppDataBase:RoomDatabase(){
    abstract fun recipeDao(): RecipeDao
    companion object {
        val DATABASE_NAME:String = "recipe_db"
    }
}