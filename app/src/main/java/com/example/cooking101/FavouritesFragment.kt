package com.example.cooking101

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooking101.Favouritiespackage.FavouritesAdapter
import com.example.cooking101.recipesPackage.AppDatabase
import com.example.cooking101.recipesPackage.Recipes
import kotlinx.coroutines.withContext


    class FavouritesFragment : Fragment() {

        private lateinit var favouriteAdapter: FavouritesAdapter
        private lateinit var favRecyclerView: RecyclerView
        private lateinit var db: AppDatabase
        //private lateinit var recipesList: List<Recipes>
        private var recipesList: MutableList<Recipes> = mutableListOf()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_favourites, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // create an instance of the database
            db = AppDatabase.getInstance(requireContext())

            CoroutineScope(Dispatchers.IO).launch {
                val recipeDao = db.recipesDao()
                recipesList = recipeDao.getAllRecipes().toMutableList()


                // Clear the list before adding new data
                recipesList.clear()
                recipesList.addAll(recipeDao.getAllRecipes().toMutableList())

                // Display the favourite recipes in a recycler view
                withContext(Dispatchers.Main) {
                    favRecyclerView = view.findViewById(R.id.favouritesRV)
                    favouriteAdapter = FavouritesAdapter(recipesList) { clickedRecipeName ->

                        // move to RecipeDetail fragment
                        val fragmentManager = requireActivity().supportFragmentManager
                        val detailFragment = RecipeDetails()

                        // Create a Bundle and set arguments for the RecipeFragment
                        val bundle = Bundle()
                        bundle.putString("recipeName", clickedRecipeName)
                        detailFragment.arguments = bundle

                        // Replace the current fragment with the RecipeFragment
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView2, detailFragment)
                            .addToBackStack(null)
                            .commit()

                    }

                    favRecyclerView.adapter = favouriteAdapter
                    favRecyclerView.layoutManager = LinearLayoutManager(context)
                    favouriteAdapter.notifyDataSetChanged()

                }
            }

        }

    }