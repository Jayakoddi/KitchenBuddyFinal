package com.example.cooking101.Favouritiespackage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.cooking101.R
import com.example.cooking101.recipesPackage.Recipes

class FavouritesAdapter (
    private var favRecipeList: List<Recipes>,
    var recipeNameClicked: ((String)-> Unit)
): RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views held by recipe item
        val recipeTitle: TextView = itemView.findViewById(R.id.fav_titleTV)
        val recipeImg: ImageView = itemView.findViewById(R.id.fav_recipeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.favourites_item, parent, false)
        return FavouritesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val currentRecipe = favRecipeList[position]
        holder.recipeTitle.text = (currentRecipe.name)
        holder.recipeImg.load(currentRecipe.image)
        {
            crossfade(true)
            transformations(
                RoundedCornersTransformation(50F)
            )
        }
        //When a recipe item is Clicked
        holder.itemView.setOnClickListener {
            recipeNameClicked(currentRecipe.name!!)
        }
    }

    override fun getItemCount(): Int {
        return favRecipeList.size
    }


}