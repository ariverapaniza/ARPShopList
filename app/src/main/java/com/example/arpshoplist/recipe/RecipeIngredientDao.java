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
public interface RecipeIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RecipeIngredient recipeIngredient);

    @Update
    void update(RecipeIngredient recipeIngredient);

    @Delete
    void delete(RecipeIngredient recipeIngredient);

    @Query("SELECT * FROM RECIPEINGREDIENT")
    LiveData<List<RecipeIngredient>> getAllIngredientsForRecipe();

    @Query("SELECT * FROM RECIPEINGREDIENT WHERE RECIPEINGREDIENTID = :recipeingredientid")
    RecipeIngredient getIngredientsForRecipeId(Integer recipeingredientid);

    @Query("SELECT * FROM RECIPEINGREDIENT WHERE QUANTITY = :quantity")
    LiveData<List<RecipeIngredient>> findByQuantity(String quantity);

    @Query("SELECT * FROM RECIPEINGREDIENT WHERE RECIPETABLEID = :recipetableid")
    RecipeIngredient findRecipeByRecipeTableId(Integer recipetableid);

    @Query("SELECT * FROM RECIPEINGREDIENT WHERE INGREDIENTTABLEID = :ingredienttableid")
    RecipeIngredient findRecipeByIngredientTableId(Integer ingredienttableid);

    // TODO: Fetching recipe retreival methods
    @Query("SELECT * FROM RECIPEINGREDIENT WHERE RECIPETABLEID = :recipetableid")
    LiveData<List<RecipeIngredient>> findRecipeIngredientsForRecipe(Integer recipetableid);

    // TODO: Update a Recipe
    @Update
    void updateRecipeIngredients(List<RecipeIngredient> recipeIngredients);

}
