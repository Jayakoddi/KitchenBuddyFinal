package com.example.cooking101

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import coil.load
import com.example.cooking101.recipesPackage.AppDatabase
import com.example.cooking101.recipesPackage.RecipeViewModel
import com.example.cooking101.recipesPackage.Recipes
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecipeDetails : Fragment() {

    private lateinit var theRecipe : Recipes
    private val recipeViewModel = RecipeViewModel()
    private var recipesList: MutableList<Recipes> = mutableListOf()
   private lateinit var db: AppDatabase
    private val database = Firebase.database
    private val recipesRef = database.getReference("Recipes")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create an instance of the database
        db = AppDatabase.getInstance(requireContext())

       val args = arguments
        val recipeName = args?.getString("recipeName")
       /* val serving = args?.getString("servings")
        val time = args?.getString("time")
        val img = args?.getString("recipeImage")
        val video = args?.getString("video")
        val ingredients = args?.getString("recipeIngredients")
        val directions = args?.getString("recipeDirections")
        val recipeId = args?.getString("id")
        val category =  args?.getString("category")
        val copy = args?.getString("copy")

                // val recipesList = recipeViewModel.getRecipes()
                //theRecipe = recipesList.find { it.name == recipeName }!!

        if (serving != null) {
            if (recipeId != null) {
                theRecipe = Recipes(category, directions, recipeId.toInt(), ingredients, recipeName, serving.toInt(), time, copy, img,video)
            }
        }*/


        CoroutineScope(Dispatchers.IO).launch {
            recipesRef.get().addOnSuccessListener {
                val allRecipes = mutableListOf<Recipes>()
                it.children.forEach { childsnap ->
                    val theRecipe = (childsnap.getValue<Recipes>()!!)
                    allRecipes.add(theRecipe)
                    recipesList = allRecipes
                }
                theRecipe = recipesList.find { it.name == recipeName }!!

                val title = view.findViewById<TextView>(R.id.titleTV)
                val recipeImg = view.findViewById<ImageView>(R.id.recipeImage)
                val servings = view.findViewById<TextView>(R.id.servesTV)
                val timing = view.findViewById<TextView>(R.id.timeTV)
                val ingredientsList = view.findViewById<TextView>(R.id.ingredientsTV)
                val descriptionList = view.findViewById<TextView>(R.id.descriptionTV)

                //title.text = theRecipe.name
                title.text = recipeName
                recipeImg.load(theRecipe.image)
                {
                    crossfade(true)
                    // placeholder image is the image used
                    // when our image url fails to load.
                    placeholder(R.drawable.ic_launcher_background)
                }


                ingredientsList.text = theRecipe.ingredients
                //ingredientsList.text = ingredients
                //descriptionList.text = directions
                descriptionList.text = theRecipe.directions
                servings.text = "Serves:" + theRecipe.serving.toString()
                //servings.text = "Serves:" + serving
                timing.text = "Time: " + theRecipe.cookTime



                //Favourite button - add the recipe to Room database
                val addToFavourites = view.findViewById<ImageButton>(R.id.favouriteButton)
                addToFavourites.setOnClickListener {
                    val toast = Toast.makeText(context, "Saved To Favourites", Toast.LENGTH_SHORT)
                   // toast.view?.setBackgroundColor(Color.BLUE) // Set the background color
                    toast.show()
                    CoroutineScope(Dispatchers.IO).launch {
                        val recipesDao = db.recipesDao()
                        recipesDao.insert(theRecipe)
                        //recipesDao.delete(theRecipe)

                    }

                }
            }
        }
    
    }
}