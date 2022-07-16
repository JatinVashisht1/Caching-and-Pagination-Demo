package com.jatinvashisht.paginationandcachingpractice.domain.pagination

import com.jatinvashisht.paginationandcachingpractice.core.Resource
import com.jatinvashisht.paginationandcachingpractice.data.remote.dto.RecipeDtoItem
import com.jatinvashisht.paginationandcachingpractice.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class RecipePaginator<Key, Item> @Inject constructor(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Flow<Resource<List<Item>>>,
    private inline val getNextKey: suspend (items: List<Item>) -> Key,
    private inline val onError: suspend (throwable: Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit,
) : Paginator<Key, Item> {
    private var currentKey = initialKey

    // this will be true as long as we are making request to datasource (database/API
    private var isMakingRequest = false
    override suspend fun loadNextItems() {
        // don't make request again if we are making request already
        if (isMakingRequest) {
            return
        }

        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        result.collectLatest {
            when (it) {
                is Resource.Error -> {
                    onError(Throwable(it.error))
                    onLoadUpdated(false)
                }
                is Resource.Loading -> {
                    onLoadUpdated(true)
                }
                is Resource.Success -> {
                    onSuccess(it.data ?: emptyList(), currentKey)
                    onLoadUpdated(false)
                }
            }
        }
    }
}