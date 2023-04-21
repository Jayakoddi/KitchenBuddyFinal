package com.example.cooking101.recipesPackage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.cooking101.R
import com.example.cooking101.categoryPackage.Category
import com.example.cooking101.categoryPackage.CategoryAdapter

class RecipeAdapter (
    var recipeList: List<Recipes>,
    var gridItemClicked: ((Recipes)-> Unit)

): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        // Views held by recipe item
        val recipeTitle: TextView = itemView.findViewById(R.id.recipe_title)
        val recipeImg: ImageView = itemView.findViewById(R.id.recipe_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.recipeTitle.text = (currentRecipe.name)
        holder.recipeImg.load(currentRecipe.image)
        {
            crossfade(true)
            transformations(
                RoundedCornersTransformation(40F)
            )
        }
        //When a recipe item is Clicked
        holder.itemView.setOnClickListener {
            gridItemClicked(currentRecipe)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

}
