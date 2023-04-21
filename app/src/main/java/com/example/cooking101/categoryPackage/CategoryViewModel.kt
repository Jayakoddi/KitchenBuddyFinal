package com.example.cooking101.categoryPackage

import androidx.lifecycle.ViewModel
import com.example.cooking101.R

class CategoryViewModel: ViewModel() {
    private val categories = listOf(
        Category(R.string.breakfast, R.drawable.breakfast),
        Category(R.string.lunch, R.drawable.lunch),
        Category(R.string.salad, R.drawable.salads),
        Category(R.string.dessert, R.drawable.dessert)
    )

    fun getCategories(): List<Category>{
        return categories
    }

}