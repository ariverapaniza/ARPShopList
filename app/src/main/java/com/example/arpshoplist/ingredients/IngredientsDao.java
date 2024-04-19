package com.example.arpshoplist.ingredients;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arpshoplist.ingredients.Ingredients;
import com.example.arpshoplist.recipe.Recipe;

import java.util.List;

@Dao
public interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Ingredients ingredients);

    @Update
    void update(Ingredients ingredients);

    @Delete
    void delete(Ingredients ingredients);

    @Query("SELECT * FROM INGREDIENTS")
    LiveData<List<Ingredients>> getAllIngredients();

    @Query("SELECT * FROM INGREDIENTS WHERE INGREDIENTID = :ingredientid")
    Ingredients getIngredientById(Integer ingredientid);

    @Query("SELECT * FROM INGREDIENTS WHERE INGREDIENTNAME = :ingredientname")
    LiveData<List<Ingredients>> getIngredient(String ingredientname);

    // TODO: Fetching recipe retreival methods
    @Query("SELECT INGREDIENTS.* FROM INGREDIENTS " +
            "INNER JOIN RECIPEINGREDIENT ON INGREDIENTS.INGREDIENTID = RECIPEINGREDIENT.INGREDIENTTABLEID " +
            "WHERE RECIPEINGREDIENT.RECIPETABLEID = :recipeId")
    LiveData<List<Ingredients>> findIngredientsForRecipe(Integer recipeId);

    // TODO: Update a Recipe
    @Update
    void updateIngredients(List<Ingredients> ingredients);

}
