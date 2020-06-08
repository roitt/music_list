package com.rbhoompally.musiclist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rbhoompally.data.Entity
import com.squareup.picasso.Picasso
import com.rbhoompally.musiclist.R
import kotlinx.android.synthetic.main.item_detail.*


class ItemDetailFragment : Fragment() {
    private var item: Entity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ENTITY)) {
                item = it.getSerializable(ARG_ITEM_ENTITY) as Entity
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the content
        item?.let {
            val artist: TextView = rootView.findViewById(R.id.artist)
            val image: ImageView = rootView.findViewById(R.id.image)
            val album: TextView = rootView.findViewById(R.id.album)

            album.text = it.collectionName
            artist.text = String.format(resources.getString(R.string.song_by), it.artistName)
            Picasso.get().load(it.artworkUrl100).into(image)
        }

        return rootView
    }

    private fun setupToolbar() {
        title_toolbar.text = item?.trackName
        back.setOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
    }

    companion object {
        const val ARG_ITEM_ENTITY = "item_entity"
    }
}
