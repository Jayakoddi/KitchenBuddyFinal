package com.example.cooking101.recipesPackage

import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RecipeRepository {


        private val database = Firebase.database
        private val recipe = database.getReference("Recipes")


        suspend fun getData(): List<Recipes> {
            val allRecipes = mutableListOf<Recipes>()

            /*recipe.get()retrieves some data from realtime database
            It returns a Task object that represents the result of the asynchronous operation.
             await() is called on the Task object, which suspends the current coroutine until the task is complete
             and returns the result. This is a common pattern in Kotlin coroutines to wait for an asynchronous operation to complete before continuing execution.
            Once the Task completes and its result is available, children is called on it to retrieve a list of child snapshots.
            forEach is called on the list of child snapshots, which iterates over each child snapshot and executes the code inside the curly braces for each one.*/


            recipe.get().await().children.forEach {
                val aRecipe = it.getValue<Recipes>()
                if(aRecipe != null){
                    allRecipes.add(aRecipe)
                }
            }
            return allRecipes
        }


}