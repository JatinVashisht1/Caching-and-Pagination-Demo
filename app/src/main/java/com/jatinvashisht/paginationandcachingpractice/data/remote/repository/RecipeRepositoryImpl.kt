package com.jatinvashisht.paginationandcachingpractice.data.remote.repository

import com.jatinvashisht.paginationandcachingpractice.core.Resource
import com.jatinvashisht.paginationandcachingpractice.data.remote.RecipeApi
import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem
import com.jatinvashisht.paginationandcachingpractice.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(private val recipeApi: RecipeApi) :
    RecipeRepository {
    override suspend fun getRecipes(
        recipe: String,
        page: Int,
        pageSize: Int,
    ):
            Flow<Resource<List<RecipeDtoItem>>> = flow {

        try {
            val recipes = recipeApi.getRecipeList(recipe = recipe)
            val startingIndex = page * pageSize
            if (startingIndex + pageSize <= recipes.size) {
                emit(Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until startingIndex + pageSize)))
            }
            emit(Resource.Success<List<RecipeDtoItem>>(data = emptyList()))
        } catch (exception: Exception) {
            emit(Resource.Error<List<RecipeDtoItem>>("unable to load data, please try again later"))
        }

    }
}