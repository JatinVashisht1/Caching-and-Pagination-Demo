package com.jatinvashisht.paginationandcachingpractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jatinvashisht.paginationandcachingpractice.data.converter.IngredientConverter
import com.jatinvashisht.paginationandcachingpractice.data.converter.MethodConverter

@Database(
    entities = [RecipeEntity::class],
    version = 1,
)
@TypeConverters(IngredientConverter::class, MethodConverter::class)
abstract class RecipeDatabase : RoomDatabase(){
        abstract val dao: RecipeDao
        companion object{
            const val  DATABASE_NAME = "recipedatabase"
        }
}