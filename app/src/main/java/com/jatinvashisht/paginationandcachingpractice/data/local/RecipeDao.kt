package com.jatinvashisht.paginationandcachingpractice.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RecipeDao{
    @Insert(onConflict = REPLACE)
    suspend fun insertRecipes(recipeEntities: List<RecipeEntity>)

    @Query("DELETE FROM recipeentity")
    suspend fun clearRecipes()

    @Query("""
        SELECT *
        FROM recipeentity
        WHERE LOWER(title) LIKE '%' || LOWER(:query) || '%'
    """)
    suspend fun searchRecipe(query: String): List<RecipeEntity>
}