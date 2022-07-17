package com.jatinvashisht.paginationandcachingpractice.data.remote.repository

import android.util.Log
import com.jatinvashisht.paginationandcachingpractice.core.Resource
import com.jatinvashisht.paginationandcachingpractice.data.local.RecipeDatabase
import com.jatinvashisht.paginationandcachingpractice.data.local.RecipeEntity
import com.jatinvashisht.paginationandcachingpractice.data.mapper.toRecipeDtoItem
import com.jatinvashisht.paginationandcachingpractice.data.mapper.toRecipeEntity
import com.jatinvashisht.paginationandcachingpractice.data.remote.RecipeApi
import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem
import com.jatinvashisht.paginationandcachingpractice.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val recipeDatabase: RecipeDatabase
) :
    RecipeRepository {
    override suspend fun getRecipes(
        recipe: String,
        page: Int,
        pageSize: Int,
        fetchFromRemote: Boolean,
    ): Resource<List<RecipeDtoItem>> {

        val recipeDao = recipeDatabase.dao


        try {
            val shouldJustLoadFromCache = !fetchFromRemote && recipeDao.searchRecipe("").isNotEmpty()
            val myRecipes: List<RecipeEntity> = if (!shouldJustLoadFromCache) {

                recipeDao.clearRecipes()
                val remoteEntities = recipeApi.getRecipeList(recipe)
                recipeDao.insertRecipes(remoteEntities.map {
                    it.toRecipeEntity()
                })
                recipeDao.searchRecipe("")
            } else {
                Log.d("reciperepository", "inside should just load from cache")
                recipeDao.searchRecipe("")
            }
            val recipes = myRecipes.map {
                it.toRecipeDtoItem()
            }
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
                } else {
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