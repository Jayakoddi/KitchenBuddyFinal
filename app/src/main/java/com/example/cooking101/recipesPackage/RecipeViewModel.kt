package com.example.cooking101.recipesPackage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel: ViewModel() {

    private var recipeRepository = RecipeRepository()
   var allRecipes: MutableLiveData<List<Recipes>> = MutableLiveData()
   var recipesList= mutableListOf<Recipes> ()
    //private var recipesList: MutableList<Recipes> = mutableListOf()
    private val database = Firebase.database
    private val recipesRef = database.getReference("Recipes")

   fun getData(){
        viewModelScope.launch {
           // var recipesList: List<Recipes>? = null
            withContext(Dispatchers.IO){
                recipesList = recipeRepository.getData().toMutableList()
            }
            allRecipes.value = recipesList!!
        }
    }





    fun getRecipes(): List<Recipes>{
        recipesList.clear()
        val recipe1 = Recipes(
          "Dinner",
            "Step 1 \n Step 2, \n Step 3",
            1,
            "Ingredient 1 \n Ingredient 2 \n Ingredient 3",
            "Chicken Alfredo",
           4,
             "30 minutes",
          "©2023 John Doe",
            "https://cookieandkate.com/images/2012/04/avocado-toast-with-tomatoes-balsamic-vinegar-basil.jpg",
            "https://www.youtube.com/watch?v=Rh4EI4luKAQ"
        )

      val recipe2 = Recipes(
            "Dessert",
          "Step 1 \n Step 2, \n Step 3",
             2,
          "Ingredient 1 \n Ingredient 2 \n Ingredient 3",
             "Chocolate Cake",
             6,
             "1 hour",
             "©2023 Jane Doe",
            "https://cookieandkate.com/images/2012/04/avocado-toast-with-tomatoes-balsamic-vinegar-basil.jpg",
            "https://www.youtube.com/watch?v=Rh4EI4luKAQ"
        )

       val recipe3 = Recipes(
             "Breakfast",
            "Step 1 \n Step 2, \n Step 3",
            3,
           "Ingredient 1 \n Ingredient 2 \n Ingredient 3",
             "Pancakes",
        2,
             "20 minutes",
             "©2023 John Doe",
           "https://lilluna.com/wp-content/uploads/2018/04/french-toast-bake-final-resize-3.jpg",
             "https://www.youtube.com/watch?v=Rh4EI4luKAQ"
        )

        val recipe4 = Recipes(
             "Breakfast",
            "Step 1 \n Step 2, \n Step 3",
           4,
            "Ingredient 1 \n Ingredient 2 \n Ingredient 3",

           "Grilled Cheese",
            2,
             "10 minutes",
             "©2023 Jane Doe",
          "https://lilluna.com/wp-content/uploads/2018/04/french-toast-bake-final-resize-3.jpg",
             "https://www.youtube.com/watch?v=Rh4EI4luKAQ"
        )

    // add the recipes objects to the list
        recipesList.add(recipe1)
        recipesList.add(recipe2)
        recipesList.add(recipe3)
        recipesList.add(recipe4)
        //allRecipes.value = recipesList

        return recipesList


    }


}