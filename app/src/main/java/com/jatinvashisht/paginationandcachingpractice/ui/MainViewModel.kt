package com.jatinvashisht.paginationandcachingpractice.ui

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatinvashisht.paginationandcachingpractice.domain.repository.RecipeRepository
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            val result = recipeRepository.getRecipes("snacks")
            Log.d("mainviewmodel", "result is $result")
        }
    }
}