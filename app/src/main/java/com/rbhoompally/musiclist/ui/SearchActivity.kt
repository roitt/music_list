package com.rbhoompally.musiclist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rbhoompally.data.Entity
import com.rbhoompally.musiclist.R
import com.rbhoompally.musiclist.adapters.SearchItemAdapter

class SearchActivity : AppCompatActivity(), SearchItemAdapter.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSearchFragment()
    }

    override fun onItemClick(item: Entity) {
        val fragment = ItemDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ItemDetailFragment.ARG_ITEM_ENTITY, item)
            }
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupSearchFragment() {
        val searchFragment = SearchFragment.newInstance()
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .add(
                R.id.main_container,
                searchFragment
            )
            .commit()
    }
}
