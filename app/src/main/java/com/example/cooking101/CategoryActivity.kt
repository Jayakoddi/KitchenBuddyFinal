package com.example.cooking101

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_category)
        //initial view is the Home view
        replaceFragments(HomeFragment())
        supportActionBar?.title = "Kitchen Buddy"
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener() {
            when (it.itemId) {
                R.id.navigation_home -> {
                    supportActionBar?.title = "Recipes"
                    replaceFragments(HomeFragment())
                }
                R.id.navigation_favorite -> {
                    supportActionBar?.title = "Favourites"
                    replaceFragments(FavouritesFragment())
                }
                R.id.navigation_search -> {
                    supportActionBar?.title = "Search"
                replaceFragments(SearchFragment())
                }

                else -> {
                }
            }
            true
        }
    }


    //function to replace frame layout with fragments
    private fun replaceFragments(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragmentContainerView2, fragment)
        fragmentTransaction.addToBackStack(null )
        fragmentTransaction.commit()
    }



}