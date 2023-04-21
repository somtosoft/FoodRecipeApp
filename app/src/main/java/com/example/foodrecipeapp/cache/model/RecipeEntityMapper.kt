package com.example.foodrecipeapp.cache.model

import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.domain.util.DomainMapper
import com.example.foodrecipeapp.util.DateUtils

class RecipeEntityMapper:DomainMapper<RecipeEntity, Recipe> {
    override fun mapToDomainModel(model: RecipeEntity): Recipe {
        return Recipe(
            id = model.id,
            title = model.title,
            publisher = model.publisher,
            featuredImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceUrl,
            ingredients =convertIngredientsToList(model.ingredients),
            dateAdded = DateUtils.longToDate(model.dateAdded),
            dateUpdated = DateUtils.longToDate(model.dateUpdated),
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeEntity {
        return RecipeEntity(
            id = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            ingredients = convertIngredientsToString(domainModel.ingredients),
            dateAdded = DateUtils.dateToLong(domainModel.dateAdded),
            dateUpdated = DateUtils.dateToLong(domainModel.dateUpdated),
            dateCached = DateUtils.dateToLong(DateUtils.createTimestamp())
        )
    }
    //"carrot, chicken" -> ["carrot", "chicken"]
    private fun convertIngredientsToList(ingredients: String): List<String>{
//        return ingredients.split(",").map { it.trim() }
        val list:ArrayList<String> = ArrayList()
        ingredients.let{ it ->
            for (ingredient in it.split(",")){
                ingredient.trim().let{
                    if(it.isNotEmpty()){
                        list.add(it)
                    }
                }
            }

        }
        return list
    }

//    ["carrot", "chicken"] -> "carrot, chicken"
    private fun convertIngredientsToString(ingredients: List<String>): String{
        val ingredientsString = StringBuilder()
        for(ingredient in ingredients){
            ingredientsString.append("$ingredient,")
        }
        return ingredientsString.toString()
    }

    fun fromEntityList(initial: List<RecipeEntity>): List<Recipe>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}