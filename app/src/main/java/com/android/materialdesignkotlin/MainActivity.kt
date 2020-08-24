package com.android.materialdesignkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mDrawerLayout: DrawerLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar =
            findViewById<View>(R.id.Toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val viewPager = findViewById<View>(R.id.ViewPager) as ViewPager
        setupViewPager(viewPager)

        val tabs = findViewById<View>(R.id.tabs) as TabLayout
        tabs.setupWithViewPager(viewPager)

        val navigationView =
            findViewById<View>(R.id.nav_view) as NavigationView
        mDrawerLayout = findViewById<View>(R.id.drawer) as DrawerLayout

        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            val indicator =
                VectorDrawableCompat.create(resources, R.drawable.ic_menu, theme)
            indicator!!.setTint(ResourcesCompat.getColor(resources, R.color.white, theme))
            supportActionBar.setHomeAsUpIndicator(indicator)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            val fm = supportFragmentManager
            val transaction =
                fm.beginTransaction()
            if (menuItem.itemId == R.id.listaNavegacion) {
                viewPager.currentItem = 0
                tabs.getTabAt(0)
            } else if (menuItem.itemId == R.id.azulejoNavegacion) {
                viewPager.currentItem = 1
                tabs.getTabAt(1)
            } else if (menuItem.itemId == R.id.cartasNavegacion) {
                viewPager.currentItem = 2
                tabs.getTabAt(2)
            }
            transaction.commit()
            // TODO: handle navigation

            // Closing drawer on item click
            mDrawerLayout!!.closeDrawers()
            true
        }
        // Adding Floating Action Button to bottom right of main view
        // Adding Floating Action Button to bottom right of main view
        val fab =
            findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { v ->
            Snackbar.make(
                v, "Agregar nuevo sitio",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter: MainActivity.Adapter =
            MainActivity.Adapter(supportFragmentManager)
        adapter.addFragment(ListContentFragment(), "Lista")
        adapter.addFragment(TileContentFragment(), "Azulejo")
        adapter.addFragment(CardContentFragment(), "Cartas")
        viewPager.adapter = adapter
    }

    internal class Adapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> =
            ArrayList()
        private val mFragmentTitleList: MutableList<String> =
            ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(
            fragment: Fragment,
            title: String
        ) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        } else if (id == android.R.id.home) {
            mDrawerLayout!!.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}