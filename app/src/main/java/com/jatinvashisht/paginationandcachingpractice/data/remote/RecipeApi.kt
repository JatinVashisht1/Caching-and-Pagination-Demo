package com.jatinvashisht.paginationandcachingpractice.data.remote

import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {
    @GET("{recipe}.json")
    suspend fun getRecipeList(@Path("recipe") recipe: String): RecipeDto
}