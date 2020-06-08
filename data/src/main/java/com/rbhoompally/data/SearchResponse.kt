package com.rbhoompally.data

data class SearchResponse(
    var resultCount: Int? = 0,
    var results: List<Entity>? = ArrayList()
)