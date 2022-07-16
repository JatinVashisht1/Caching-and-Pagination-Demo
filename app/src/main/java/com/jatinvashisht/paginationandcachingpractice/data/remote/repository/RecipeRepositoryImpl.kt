package com.jatinvashisht.paginationandcachingpractice.data.remote.repository

import android.util.Log
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
            Resource<List<RecipeDtoItem>> {

        try {
            val recipes = recipeApi.getRecipeList(recipe = recipe)
            val startingIndex = page * pageSize
            Log.d(
                "reciperepository",
                "starting index is $startingIndex, page is $page, pageSize is $pageSize, recipes size is ${recipes.size}"
            )
            val endingIndex = startingIndex + pageSize

            if (startingIndex < recipes.size) {
                return if (endingIndex < recipes.size) {
                    Log.d(
                        "reciperepository",
                        "starting index is $startingIndex, ending index is ${startingIndex + pageSize}, recipe is ${
                            recipes.slice(
                                startingIndex until startingIndex + pageSize
                            )
                        }"
                    )
                    Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until startingIndex + pageSize))
                }else{
                    Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until recipes.size))
                }
            } else {
                return Resource.Success<List<RecipeDtoItem>>(data = emptyList())
            }
        } catch (exception: Exception) {
            return Resource.Error<List<RecipeDtoItem>>("unable to load data, please try again later")
        }

    }
}