package com.jatinvashisht.paginationandcachingpractice.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem
import com.jatinvashisht.paginationandcachingpractice.domain.pagination.RecipePaginator
import com.jatinvashisht.paginationandcachingpractice.domain.repository.RecipeRepository
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    var state by mutableStateOf<HomeScreenState>(HomeScreenState())
    val recipePaginator = RecipePaginator<Int, RecipeDtoItem>(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            recipeRepository.getRecipes("snacks", state.page, 20)
        },
        getNextKey = { items ->
            state.page + 1
        },
        onError = { throwable ->
            state = state.copy(
                error = throwable?.localizedMessage
                    ?: "unable to load items, please try again later"
            )
        }
    ) { newItems, newKey ->
        state = state.copy(
            items = state.items + newItems,
            page = newKey,
            endReached = newItems.isEmpty(),
        )
    }

    init {
        viewModelScope.launch {
            loadNextItems()
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            recipePaginator.loadNextItems()
        }
    }
}