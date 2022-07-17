package com.jatinvashisht.paginationandcachingpractice.data.mapper

import com.jatinvashisht.paginationandcachingpractice.data.local.RecipeEntity
import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem

fun RecipeEntity.toRecipeDtoItem(): RecipeDtoItem = RecipeDtoItem(
    imageUrl = imageUrl,
    ingredient = ingredient,
    method = method,
    tag = tag,
    title = title,
)

fun RecipeDtoItem.toRecipeEntity(): RecipeEntity = RecipeEntity(
    imageUrl = imageUrl,
    ingredient = ingredient,
    method = method,
    tag = tag,
    title = title
)