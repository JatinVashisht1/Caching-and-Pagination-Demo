package com.jatinvashisht.paginationandcachingpractice.domain.repository

import com.jatinvashisht.paginationandcachingpractice.core.Resource
import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipes(recipe: String, page: Int, pageSize: Int, fetchFromRemote: Boolean): Resource<List<RecipeDtoItem>>
}