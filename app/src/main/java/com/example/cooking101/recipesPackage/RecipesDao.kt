package com.example.cooking101.recipesPackage

import android.content.Context
import androidx.room.*

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipes)

    @Update
    fun update(recipe: Recipes)

    @Delete
    fun delete(recipe: Recipes)

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): List<Recipes>

    @Query("SELECT * FROM Recipes WHERE name= :name")
    fun getRecipeById(name: String): Recipes?
}

@Database(entities = [Recipes::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "RecipeTable"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}