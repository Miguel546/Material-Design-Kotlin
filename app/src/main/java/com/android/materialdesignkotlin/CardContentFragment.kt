package com.android.materialdesignkotlin

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardContentFragment : Fragment() {
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
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return recyclerView
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_card_content, parent, false)) {
        var picture: ImageView
        var name: TextView
        var description: TextView

        init {
            picture =
                itemView.findViewById<View>(R.id.card_image) as ImageView
            name = itemView.findViewById<View>(R.id.card_title) as TextView
            description = itemView.findViewById<View>(R.id.card_text) as TextView
            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("position", adapterPosition)
                context.startActivity(intent)
            }

            // Adding Snackbar to Action Button inside card
            val button =
                itemView.findViewById<View>(R.id.action_button) as Button
            button.setOnClickListener { v ->
                Snackbar.make(
                    v, "Acción presionada en pantalla de cartas",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            val favoriteImageButton =
                itemView.findViewById<View>(R.id.favorite_button) as ImageButton
            favoriteImageButton.setOnClickListener { v ->
                Snackbar.make(
                    v, "Añadido a favorito",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            val shareImageButton =
                itemView.findViewById<View>(R.id.share_button) as ImageButton
            shareImageButton.setOnClickListener { v ->
                Snackbar.make(
                    v, "Compartir artículo",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * Adapter to display recycler view.
     */
    class ContentAdapter(context: Context) :
        RecyclerView.Adapter<ViewHolder>() {
        private val mPlaces: Array<String>
        private val mPlaceDesc: Array<String>
        private val mPlacePictures: Array<Drawable?>
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.size])
            holder.name.text = mPlaces[position % mPlaces.size]
            holder.description.text = mPlaceDesc[position % mPlaceDesc.size]
        }

        override fun getItemCount(): Int {
            return LENGTH
        }

        companion object {
            // Set numbers of Card in RecyclerView.
            private const val LENGTH = 18
        }

        init {
            val resources = context.resources
            mPlaces = resources.getStringArray(R.array.places)
            mPlaceDesc = resources.getStringArray(R.array.place_desc)
            val a = resources.obtainTypedArray(R.array.places_picture)
            mPlacePictures = arrayOfNulls(a.length())
            for (i in mPlacePictures.indices) {
                mPlacePictures[i] = a.getDrawable(i)
            }
            a.recycle()
        }
    }
}