package com.example.cooking101

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooking101.recipesPackage.RecipeAdapter
import com.example.cooking101.recipesPackage.RecipeViewModel
import com.example.cooking101.recipesPackage.Recipes
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecipesInACategory : Fragment() {

    private val recipeViewModel = RecipeViewModel()
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var categoryRV: RecyclerView
   // private lateinit var recipesList: List<Recipes>
   private var recipesList: MutableList<Recipes> = mutableListOf()
    private val database = Firebase.database
    private val recipesRef = database.getReference("Recipes")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes_in_a_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val categoryTitle = args?.getString("categoryTitle")

        val title = view.findViewById<TextView>(R.id.CategoryTV)
        title.text = categoryTitle

        // val allRecipes = recipeViewModel.getRecipes()
        // recipesList.clear()

        //recipesList = (allRecipes.filter { it.category == title.text }).toMutableList()
        CoroutineScope(Dispatchers.IO).launch {
            recipesRef.get().addOnSuccessListener {
                val allRecipes = mutableListOf<Recipes>()
                it.children.forEach { childsnap ->
                    val theRecipe = (childsnap.getValue<Recipes>()!!)
                    allRecipes.add(theRecipe)
                    recipesList = allRecipes

                }
                recipeAdapter.recipeList=recipesList.filter {it.category == title.text }
                recipeAdapter.notifyDataSetChanged()

               // recipeAdapter.notifyDataSetChanged()
            }

           // withContext(Dispatchers.Main) {
                //initialize recyclerView
                categoryRV = view.findViewById(R.id.recipeRV)
                recipeAdapter = RecipeAdapter(recipesList) { clickedRecipe ->

                    // move to RecipesInACategory fragment
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

                categoryRV.layoutManager = GridLayoutManager(context, 2)
                categoryRV.adapter = recipeAdapter
            //}
        }
    }


}