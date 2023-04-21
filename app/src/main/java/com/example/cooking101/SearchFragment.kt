package com.example.cooking101


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooking101.recipesPackage.RecipeViewModel
import com.example.cooking101.recipesPackage.Recipes
import com.example.cooking101.recipesPackage.SearchAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private val recipesViewModel = RecipeViewModel()
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var recyclerView2: RecyclerView
    private var recipesList: MutableList<Recipes> = mutableListOf()
    private val database = Firebase.database
    private val recipesRef = database.getReference("Recipes")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_search, container, false)
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Set up your RecyclerView and its adapter
        setHasOptionsMenu(true) // Enable options menu for this fragment

        return view
    }

    // calling on create option menu
    // layout to inflate our menu file.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // below line is to get our inflater
        //val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView: SearchView = searchItem.getActionView() as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })

    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        var filteredlist = mutableListOf<Recipes>()

        // running a for loop to compare elements.
        for (item in recipesList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name?.lowercase()?.contains(text.lowercase()) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.

            searchAdapter.filterList(filteredlist)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            recipesRef.get().addOnSuccessListener {
                val allRecipes = mutableListOf<Recipes>()
                it.children.forEach { childsnap ->
                    val theRecipe = (childsnap.getValue<Recipes>()!!)
                    allRecipes.add(theRecipe)
                    recipesList = allRecipes
                }

                searchAdapter.recipeList=recipesList
                searchAdapter.notifyDataSetChanged()
            }


            withContext(Dispatchers.Main) {

                // Create the adapter and set it for the RecyclerView
                // Initialize the RecyclerView
                recyclerView2 = view.findViewById(R.id.idRVRecipes)
                recyclerView2.layoutManager = LinearLayoutManager(requireContext())
                searchAdapter = SearchAdapter(recipesList) { clickedRecipe ->

                    // move to RecipesDetail fragment
                    val fragmentManager = requireActivity().supportFragmentManager
                    val detailFragment = RecipeDetails()

                    // Create a Bundle and set arguments for the RecipeFragment
                    val bundle = Bundle()
                    bundle.putString("recipeName", clickedRecipe.name)
                    detailFragment.arguments = bundle

                    // Replace the current fragment with the RecipeFragment
                    fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, detailFragment)
                        .addToBackStack(null)
                        .commit()

                }
                recyclerView2.adapter = searchAdapter
                //searchAdapter.notifyDataSetChanged()

            }
        }

    }

}