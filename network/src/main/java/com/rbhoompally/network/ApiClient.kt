package com.rbhoompally.network

import com.rbhoompally.data.SearchResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun search(term: String): Observable<SearchResponse> {
        return apiService.search(term).subscribeOn(Schedulers.io()).hide();
    }
}