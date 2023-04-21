package com.example.cooking101

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooking101.categoryPackage.Category
import com.example.cooking101.categoryPackage.CategoryAdapter
import com.example.cooking101.categoryPackage.CategoryViewModel


class HomeFragment : Fragment() {

    //get a reference to the category view Model
    private val categoryViewModel = CategoryViewModel()

    //create an instance of the adapter, recyclerView and the data array
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryList: List<Category>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get all data for the categories which include the title and an image
        categoryList = categoryViewModel.getCategories()
        categoryAdapter = CategoryAdapter(categoryList) { clickedCategory ->
            // move to RecipesInACategory fragment
            val fragmentManager = requireActivity().supportFragmentManager
            val recipeFragment = RecipesInACategory()

            // Create a Bundle and set arguments for the RecipeFragment
            val bundle = Bundle()
            bundle.putString("categoryTitle", clickedCategory)
            recipeFragment.arguments = bundle

            // Replace the current fragment with the RecipeFragment
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, recipeFragment)
                .addToBackStack(null)
                .commit()
        }
            //initialize recyclerView
            recyclerView = view.findViewById(R.id.categoryRV)
            recyclerView.adapter = categoryAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun loadRecipeData(){

    }
}