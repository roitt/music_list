package com.rbhoompally.network

import com.rbhoompally.data.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

    @GET("search")
    fun search(@Query("term") term: String): Observable<SearchResponse>
}