package com.example.cooking101.recipesPackage


import androidx.room.*

@Entity
data class Recipes(
    @ColumnInfo(name = "category")
    var category: String? = null,
    //var directions:MutableList<String>? = null,

    @ColumnInfo(name = "directions")
    var directions:String? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,
    //var ingredients: MutableList<String>? = null,
    @ColumnInfo(name = "ingredients")
    var ingredients: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "serving")
    var serving: Int? = null,

    @ColumnInfo(name = "cookTime")
    var cookTime: String? = null,

    @ColumnInfo(name = "copyright")
    var copyright: String? = null,

    @ColumnInfo(name = "image")
    var image: String? = null,

    @ColumnInfo(name = "videoLink")
    var videoLink: String? = null
){
    @Ignore
    constructor() : this("", "", 0, "", "", 0, "", "", "", "")
}


