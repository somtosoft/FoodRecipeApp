package com.example.foodrecipeapp.network.responses

import com.example.foodrecipeapp.network.model.RecipeNetworkEntity
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int,
    @SerializedName("results")
    var recipes: List<RecipeNetworkEntity>
) {
}