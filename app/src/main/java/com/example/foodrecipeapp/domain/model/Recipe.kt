package com.example.foodrecipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int?=null,
    val title: String?="",
    val publisher: String?="",
    val featuredImage: String?="",
    val rating: Int?=null,
    val sourceUrl: String?="",
    val description: String?="",
    val cookingInstructions: String?="",
    val ingredients: List<String>?=null,
    val dateAdded: String?="",
    val dateUpdated: String?="",
):Parcelable
