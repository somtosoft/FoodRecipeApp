package com.example.foodrecipeapp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "publisher")
    val publisher: String,

    @ColumnInfo(name = "featured_image")
    val featuredImage: String,

    @ColumnInfo(name = "rating")
    val rating: Int,

    @ColumnInfo(name = "source_url")
    val sourceUrl: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "cooking_instructions")
    val cookingInstructions: String,

    @ColumnInfo(name= "ingredients")
    val ingredients: String,

    @ColumnInfo(name = "date_added")
    val dateAdded: Long,

    @ColumnInfo(name = "date_updated")
    val dateUpdated: Long,

    @ColumnInfo(name = "date_refreshed")
    val dateRefreshed: Long,
)