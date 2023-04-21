package com.example.cooking101.categoryPackage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cooking101.R


class CategoryAdapter(

    private val categoryList: List<Category>,
    var rowItemClicked: ((String)-> Unit)
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        // Views held by category cell
        val heading: TextView = itemView.findViewById(R.id.category_title)
        val picture: ImageView = itemView.findViewById(R.id.category_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.picture.setImageResource(currentItem.imageResourceId)
        holder.heading.setText(currentItem.stringResourceId)

        // When a category item is Clicked
        holder.itemView.setOnClickListener {
            // rowItemClicked(currentItem.stringResourceId.toString())
            rowItemClicked(holder.heading.text.toString())
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


}
