package com.rbhoompally.musiclist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rbhoompally.data.Entity
import com.rbhoompally.musiclist.R
import com.squareup.picasso.Picasso

class SearchItemAdapter(private val listener: Listener): RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {
    private var values: List<Entity> = ArrayList()

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Entity
            listener.onItemClick(item)
        }
    }

    fun update(values: List<Entity>) {
        Log.d("Rohit", "Items: ${values.size}")
        this.values = values
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.trackName.orEmpty()
        holder.subTitle.text = String.format(holder.itemView.resources.getString(R.string.song_by),
            item.artistName.orEmpty())
        holder.tertiaryTitle.text = item.collectionName
        Picasso.get().load(item.artworkUrl60).into(holder.thumbnail)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        val title: TextView = view.findViewById(R.id.title)
        val subTitle: TextView = view.findViewById(R.id.subtitle)
        val tertiaryTitle: TextView = view.findViewById(R.id.tertiary_title)
    }

    interface Listener {
        fun onItemClick(item: Entity)
    }
}