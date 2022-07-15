package com.jatinvashisht.paginationandcachingpractice.di

import com.jatinvashisht.paginationandcachingpractice.core.Constants
import com.jatinvashisht.paginationandcachingpractice.data.remote.RecipeApi
import com.jatinvashisht.paginationandcachingpractice.data.remote.repository.RecipeRepositoryImpl
import com.jatinvashisht.paginationandcachingpractice.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesRecipeApi(): RecipeApi = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RecipeApi::class.java)

    @Provides
    fun providesRecipeRepository(recipeApi: RecipeApi): RecipeRepository = RecipeRepositoryImpl(recipeApi = recipeApi)
}