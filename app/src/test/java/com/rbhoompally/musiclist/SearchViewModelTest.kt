package com.rbhoompally.musiclist

import com.rbhoompally.musiclist.viewmodels.SearchViewModel
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {
    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setup() {
        searchViewModel = SearchViewModel()
    }

    @Test
    fun onSearchTextChanged_emitToTextChagedStream() {
        val textChanged = searchViewModel.textChangedStream.test()
        searchViewModel.onSearchTextChanged("test")
        searchViewModel.onSearchTextChanged("test_2")
        textChanged.assertValues("test", "test_2")
    }
}