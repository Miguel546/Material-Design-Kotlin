package com.android.materialdesignkotlin

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TileContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TileContentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(
            R.layout.recycler_view, container, false
        ) as RecyclerView
        val adapter = ContentAdapter(recyclerView.context)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        // Set padding for Tiles
        val tilePadding = resources.getDimensionPixelSize(R.dimen.tile_padding)
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        return recyclerView
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_tile_content, parent, false)) {
        var picture: ImageView
        var name: TextView

        init {
            picture =
                itemView.findViewById<View>(R.id.tile_picture) as ImageView
            name = itemView.findViewById<View>(R.id.tile_title) as TextView
            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("position", adapterPosition)
                context.startActivity(intent)
            }
        }
    }

    /**
     * Adapter to display recycler view.
     */
    class ContentAdapter(context: Context) :
        RecyclerView.Adapter<ViewHolder>() {
        private val mPlaces: Array<String>
        private val mPlacePictures: Array<Drawable?>
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.size])
            holder.name.text = mPlaces[position % mPlaces.size]
        }

        override fun getItemCount(): Int {
            return LENGTH
        }

        companion object {
            // Set numbers of List in RecyclerView.
            private const val LENGTH = 18
        }

        init {
            val resources = context.resources
            mPlaces = resources.getStringArray(R.array.places)
            val a = resources.obtainTypedArray(R.array.places_picture)
            mPlacePictures = arrayOfNulls(a.length())
            for (i in mPlacePictures.indices) {
                mPlacePictures[i] = a.getDrawable(i)
            }
            a.recycle()
        }
    }
}