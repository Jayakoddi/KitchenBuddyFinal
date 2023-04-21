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
import com.example.cooking101.categoryPackage.CategoryAdapter

class SearchAdapter (
    var recipeList: List<Recipes>,
    var recipeClicked: ((Recipes)-> Unit)


): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.search_item,
            parent, false
        )

        // at last we are returning our view holder
        // class with our item View File.
        return SearchViewHolder(itemView)
    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: List<Recipes>) {
        // below line is to add our filtered
        // list in our course array list.
        recipeList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        // Views held by recipe item
        val recipeTitle: TextView = itemView.findViewById(R.id.titleTV)
        val recipeImg: ImageView = itemView.findViewById(R.id.recipeImage)
    }


  /*  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchViewHolder(itemview)
    }*/


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
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
            recipeClicked(currentRecipe)
        }

    }

    override fun getItemCount(): Int {
        return recipeList.size
    }



}
