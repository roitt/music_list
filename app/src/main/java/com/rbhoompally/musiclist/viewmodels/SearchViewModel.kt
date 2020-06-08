package com.rbhoompally.musiclist.viewmodels

import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel;
import com.rbhoompally.data.Entity
import com.rbhoompally.musiclist.util.SearchHistory
import com.rbhoompally.network.ApiClient
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class SearchViewModel : ViewModel() {
    private val bin: CompositeDisposable = CompositeDisposable()

    @VisibleForTesting
    val textChangedStream: BehaviorSubject<String> = BehaviorSubject.create()
    @VisibleForTesting
    val searchResultStream: BehaviorSubject<List<Entity>> = BehaviorSubject.create()
    val searchHistory = SearchHistory(10)

    init {
        subscribeToTextChanges()
    }

    private fun subscribeToTextChanges() {
        bin.add(
            textChangedStream
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe { text ->
                    Log.d("Rohit", "Searching for: $text")
                    ApiClient
                        .search(text)
                        .filter { response ->
                            response.resultCount?.let { it > 0 } ?: false
                        }
                        .subscribe({ response ->
                            response.results?.let {
                                searchResultStream.onNext(it)
                            }
                        }, { error ->
                            Log.d("Rohit", "Handle error: $error")
                            searchResultStream.onError(IllegalStateException("Error fetching search results"))
                        })
                })
    }

    fun onSearchTextChanged(text: String) {
        textChangedStream.onNext(text)
    }

    fun onSearchImeClicked(text: String, context: Context?) {
        context?.let {
            searchHistory.addToSearches(context, text)
        }
    }

    fun searchResultStream(): Observable<List<Entity>> {
        return searchResultStream.hide()
    }
}
