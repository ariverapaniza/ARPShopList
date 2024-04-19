package com.example.arpshoplist.ingredients;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arpshoplist.UserRoomDatabase;
import com.example.arpshoplist.ingredients.Ingredients;
import com.example.arpshoplist.ingredients.IngredientsDao;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class IngredientsRepository {
    private UserRoomDatabase db;
    private IngredientsDao ingredientsDao;
    private LiveData<List<Ingredients>> getAllIngredients;
    private Ingredients ingredients;

    public IngredientsRepository(Application application){
        db = UserRoomDatabase.getDatabase(application);
        ingredientsDao = db.ingredientsDao();
        getAllIngredients = ingredientsDao.getAllIngredients();
    }

    public Long insert(Ingredients ingredients) {
        Callable<Long> insertCallable = () -> ingredientsDao.insert(ingredients);
        try {
            return UserRoomDatabase.databaseWriteExecutor.submit(insertCallable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1L;
    }


    public void update(Ingredients ingredients){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            ingredientsDao.update(ingredients);
        });
    }

    public void delete(Ingredients ingredients){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            ingredientsDao.delete(ingredients);
        });
    }

    public LiveData<List<Ingredients>> getAllIngredients(){
        return getAllIngredients;
    }

    public LiveData<List<Ingredients>> getIngredient(String ingredientname){
        return ingredientsDao.getIngredient(ingredientname);
    }

    public Ingredients getIngredientById(Integer ingredientid) {
        Callable c = () -> {
            Ingredients ingredients = ingredientsDao.getIngredientById(ingredientid);
            return ingredients;
        };
        Future<Ingredients> future = UserRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            ingredients = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    // TODO: Fetching recipe retreival methods
    public LiveData<List<Ingredients>> findIngredientsForRecipe(Integer recipeId) {
        return ingredientsDao.findIngredientsForRecipe(recipeId);
    }

    // TODO: Update a Recipe
    public void updateIngredients(List<Ingredients> ingredients) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            ingredientsDao.updateIngredients(ingredients);
        });
    }
}
