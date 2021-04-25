package com.android.materialdesignkotlin

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val collapsingToolbar =
            findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout

        collapsingToolbar.setTitle("Macchu Picchu");
        val position = intent.getIntExtra("position", 0)
        val resources = resources
        val places = resources.getStringArray(R.array.places)
        collapsingToolbar.title = places[position % places.size]
        val placeDetails =
            resources.getStringArray(R.array.place_details)
        val placeDetail = findViewById<View>(R.id.place_detail) as TextView
        placeDetail.text = placeDetails[position % placeDetails.size]
        val placeLocations =
            resources.getStringArray(R.array.place_locations)
        val placeLocation =
            findViewById<View>(R.id.place_location) as TextView
        placeLocation.text = placeLocations[position % placeLocations.size]
        val placePictures = resources.obtainTypedArray(R.array.places_picture)
        val placePicture =
            findViewById<View>(R.id.image) as ImageView
        placePicture.setImageDrawable(placePictures.getDrawable(position % placePictures.length()))
        placePictures.recycle()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}

