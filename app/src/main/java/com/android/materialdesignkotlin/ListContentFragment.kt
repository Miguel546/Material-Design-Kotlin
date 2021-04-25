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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListContentFragment : Fragment() {
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
        RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_list_content, parent, false)) {
        var avator: ImageView
        var name: TextView
        var description: TextView

        init {
            avator =
                itemView.findViewById<View>(R.id.list_avatar) as ImageView
            name = itemView.findViewById<View>(R.id.list_title) as TextView
            description = itemView.findViewById<View>(R.id.list_desc) as TextView
            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("position", adapterPosition)
                context.startActivity(intent)
            }
        }
    }

    class ContentAdapter(context: Context) :
        RecyclerView.Adapter<ViewHolder>() {
        private val mPlaces: Array<String>
        private val mPlaceDesc: Array<String>
        private val mPlaceAvators: Array<Drawable?>
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.avator.setImageDrawable(mPlaceAvators[position % mPlaceAvators.size])
            holder.name.text = mPlaces[position % mPlaces.size]
            holder.description.text = mPlaceDesc[position % mPlaceDesc.size]
        }

        override fun getItemCount(): Int {
            return LENGTH
        }

        companion object {
            private const val LENGTH = 18
        }

        init {
            val resources = context.resources
            mPlaces = resources.getStringArray(R.array.places)
            mPlaceDesc = resources.getStringArray(R.array.place_desc)
            val a = resources.obtainTypedArray(R.array.place_avator)
            mPlaceAvators = arrayOfNulls(a.length())
            for (i in mPlaceAvators.indices) {
                mPlaceAvators[i] = a.getDrawable(i)
            }
            a.recycle()
        }
    }
}