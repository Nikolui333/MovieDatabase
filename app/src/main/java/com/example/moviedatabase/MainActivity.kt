package com.example.moviedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.moviedatabase.Tabs.ActionMovieCatalog
import com.example.moviedatabase.Tabs.Panel
import com.example.moviedatabase.Tabs.categories.CatalogCategories
import com.example.moviedatabase.Tabs.films.CatalogFilms
import com.example.moviedatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        supportFragmentManager.beginTransaction().replace(R.id.content, Panel()).commit()

        binding?.bottomNav?.setOnItemSelectedListener { item ->

            when(item.itemId) {
                R.id.panelItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, Panel()).commit()
                R.id.catalogFilmsItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, CatalogFilms()).commit()
                R.id.actionMovieCatalogBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, ActionMovieCatalog()).commit()
                R.id.catalogCategoriesItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, CatalogCategories()).commit()
            }

            return@setOnItemSelectedListener true
        }

        binding?.bottomNav?.selectedItemId = R.id.panelItemBottomNav
    }

}