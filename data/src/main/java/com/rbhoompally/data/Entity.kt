package com.rbhoompally.data

import java.io.Serializable

data class Entity(
    var artistName: String? = null,
    var kind: String? = null,
    var collectionName: String? = null,
    var trackName: String? = null,
    var artworkUrl30: String? = null,
    var artworkUrl60: String? = null,
    var artworkUrl100: String? = null,
    var trackPrice: String? = null,
    var collectionPrice: String? = null,
    var trackExplicitness: String? = null,
    var trackTimeMillis: Long? = null,
    var currency: String? = null,
    var primaryGenreName: String? = null
): Serializable