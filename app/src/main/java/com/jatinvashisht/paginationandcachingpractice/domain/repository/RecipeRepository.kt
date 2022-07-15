package com.jatinvashisht.paginationandcachingpractice.domain.repository

import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem

interface RecipeRepository {
    suspend fun getRecipes(recipe: String): List<RecipeDtoItem>
}