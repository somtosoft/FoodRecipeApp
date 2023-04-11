package com.example.foodrecipeapp.network.responses

import com.example.foodrecipeapp.network.model.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
interface RecipeRetrofitService {
//    Search for recipes
    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): RecipeSearchResponse

//    Find recipe by id
    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): RecipeDto

}