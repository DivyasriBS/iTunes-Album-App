package com.divyasri.itunes.data.models

data class SearchResponse(
    val resultCount: Int,
    val results: List<Results>
)