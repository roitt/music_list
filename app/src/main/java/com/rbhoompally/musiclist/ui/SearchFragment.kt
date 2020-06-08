package com.rbhoompally.musiclist.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import com.rbhoompally.musiclist.R
import com.rbhoompally.musiclist.adapters.SearchItemAdapter
import com.rbhoompally.musiclist.viewmodels.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.search_fragment.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.view.KeyEvent
import android.widget.ArrayAdapter

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val vm: SearchViewModel by viewModels()
    private val bin: CompositeDisposable = CompositeDisposable()
    private lateinit var adapter: SearchItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSearchBar()
        setupRecyclerView(item_list)

        // Subscriptions
        subscribeToSearchResults()
    }

    private fun subscribeToSearchResults() {
        bin.add(
            vm.searchResultStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    adapter.update(list)
                }, {
                    view?.let {
                        Snackbar.make(
                            it.findViewById(R.id.container),
                            R.string.search_error,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
        )
    }

    private fun setupSearchBar() {
        search_bar.threshold = 1
        search_bar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s?.trim()?.toString().orEmpty()

                if (query.isEmpty()) {
                    frameLayout.visibility = View.GONE
                    empty_search.visibility = View.VISIBLE
                    close.visibility = View.GONE
                    return
                } else {
                    frameLayout.visibility = View.VISIBLE
                    empty_search.visibility = View.GONE
                    close.visibility = View.VISIBLE
                }

                vm.onSearchTextChanged(query)
            }
        })

        close.setOnClickListener {
            search_bar.text.clear()
        }

        search_bar.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    context?.let {
                        vm.onSearchImeClicked(search_bar.text.toString(), context)
                        updateSearchHistory(it)
                    }
                    return true
                }
                return false
            }
        })

        search_bar.setOnFocusChangeListener { v, hasFocus ->
            context?.let {
                Log.d("Rohit", "Focus changed")
                if (hasFocus) {
                    updateSearchHistory(it)
                }
            }
        }
    }

    private fun updateSearchHistory(context: Context) {
        val history = vm.searchHistory.getAllSearches(context)
        Log.d("Rohit", "on focus $history")
        val adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_dropdown_item_1line,
            history)
        search_bar.setAdapter(adapter)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = SearchItemAdapter(activity as SearchActivity)
        recyclerView.adapter = adapter
    }
}
