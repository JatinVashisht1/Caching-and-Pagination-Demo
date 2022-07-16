package com.jatinvashisht.paginationandcachingpractice.ui

import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem

data class HomeScreenState(
    val isLoading: Boolean = false,
    val items: List<RecipeDtoItem> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
