package com.example.arpshoplist.recipe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM RECIPE")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM RECIPE WHERE TITLE = :title")
    LiveData<List<Recipe>> findByTitle(String title);

    @Query("SELECT * FROM RECIPE WHERE USERRECIPEID = :userrecipeid")
    Recipe findRecipeByUserId(Integer userrecipeid);

    @Query("SELECT * FROM RECIPE WHERE RECIPEID = :recipeid")
    Recipe getByRecipeId(Integer recipeid);

    @Query("SELECT * FROM RECIPE WHERE USERRECIPEID = :userrecipeid")
    LiveData<List<Recipe>> getRecipesByUserId(Integer userrecipeid);

    // TODO: Update a Recipe
    @Update
    void updateRecipe(Recipe recipe);


}
