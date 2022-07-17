package com.jatinvashisht.paginationandcachingpractice.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        items(state.items.size) { index ->
            Log.d("HomeScreen", "item size is ${state.items.size} and index is $index")
            val item = state.items[index]

            // below statement is a part of side effect
            LaunchedEffect(key1 = index >= state.items.size - 5 && !state.endReached && !state.isLoading){
                    Log.d("If tag", "entered if statement")
                    viewModel.loadNextItems()
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = item.title, fontSize = 20.sp)
            }
        }
        item {
            if (state.isLoading) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.Center){
                    CircularProgressIndicator()
                }
            }
        }
    }
}