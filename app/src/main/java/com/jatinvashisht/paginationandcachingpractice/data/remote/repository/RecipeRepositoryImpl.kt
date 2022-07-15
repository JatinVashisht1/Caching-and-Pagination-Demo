package com.jatinvashisht.paginationandcachingpractice.data.remote.repository

import com.jatinvashisht.paginationandcachingpractice.data.remote.RecipeApi
import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem
import com.jatinvashisht.paginationandcachingpractice.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(private val recipeApi: RecipeApi): RecipeRepository {
    override suspend fun getRecipes(recipe: String): List<RecipeDtoItem> {
        val recipes = recipeApi.getRecipeList(recipe = recipe)
        return recipes
    }
}