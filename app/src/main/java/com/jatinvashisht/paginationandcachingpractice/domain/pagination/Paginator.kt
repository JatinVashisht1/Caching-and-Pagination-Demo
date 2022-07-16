package com.jatinvashisht.paginationandcachingpractice.domain.pagination

interface Paginator <Key, Item> {
    suspend fun loadNextItems()
}