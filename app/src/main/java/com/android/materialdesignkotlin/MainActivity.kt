package com.android.materialdesignkotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
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

            mDrawerLayout!!.closeDrawers()
            true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if(viewPager.currentItem == 0){
                    navigationView.menu.getItem(0).setChecked(true)
                }else if(viewPager.currentItem == 1){
                    navigationView.menu.getItem(1).setChecked(true)
                }else if(viewPager.currentItem == 2){
                    navigationView.menu.getItem(2).setChecked(true)
                }

            }
        })

        navigationView.menu.getItem(0).setChecked(true)
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
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            Toast.makeText(applicationContext, "Click en compartir", Toast.LENGTH_SHORT).show()
            val intent = Intent()
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Gracias por descargar esta aplicación")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Mail enviado desde mi app")
            intent.action = Intent.ACTION_SEND
            val chooseIntent = Intent.createChooser(intent, "Elija una opcion")
            startActivity(chooseIntent)
            return true
        } else if (id == android.R.id.home) {
            mDrawerLayout!!.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }


}