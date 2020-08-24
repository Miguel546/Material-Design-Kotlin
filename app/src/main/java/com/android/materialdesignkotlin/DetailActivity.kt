package com.android.materialdesignkotlin

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Set Collapsing Toolbar layout to the screen
        val collapsingToolbar =
            findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));
        val postion = intent.getIntExtra("position", 0)
        val resources = resources
        val places = resources.getStringArray(R.array.places)
        collapsingToolbar.title = places[postion % places.size]
        val placeDetails =
            resources.getStringArray(R.array.place_details)
        val placeDetail = findViewById<View>(R.id.place_detail) as TextView
        placeDetail.text = placeDetails[postion % placeDetails.size]
        val placeLocations =
            resources.getStringArray(R.array.place_locations)
        val placeLocation =
            findViewById<View>(R.id.place_location) as TextView
        placeLocation.text = placeLocations[postion % placeLocations.size]
        val placePictures = resources.obtainTypedArray(R.array.places_picture)
        val placePicutre =
            findViewById<View>(R.id.image) as ImageView
        placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()))
        placePictures.recycle()
    }
}