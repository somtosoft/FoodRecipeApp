package com.example.foodrecipeapp.network.model

import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.domain.util.DomainMapper

class RecipeNetworkMapper:DomainMapper<RecipeNetworkEntity, Recipe> {
    override fun mapToDomainModel(model: RecipeNetworkEntity): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            publisher = model.publisher,
            featuredImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceUrl,
            description = model.description,
            cookingInstructions = model.cookingInstructions,
            ingredients = model.ingredients?: listOf(),
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeNetworkEntity {
       return  RecipeNetworkEntity(
            pk = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients?: listOf(),
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated
       )
    }


    fun fromEntityList(initial: List<RecipeNetworkEntity>): List<Recipe>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeNetworkEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}