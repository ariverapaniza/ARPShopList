package com.example.arpshoplist.recipe;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arpshoplist.UserRoomDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class
RecipeRepository {

    private UserRoomDatabase db;

    private RecipeDao recipeDao;

    private LiveData<List<Recipe>> allRecipes;

    private Recipe recipe;

    public RecipeRepository(Application application){
        db = UserRoomDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.getAllRecipes();
    }

    public Long insert(Recipe recipe) {
        Callable<Long> insertCallable = () -> recipeDao.insert(recipe);
        try {
            return UserRoomDatabase.databaseWriteExecutor.submit(insertCallable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1L;
    }


    public void update(Recipe recipe){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            recipeDao.update(recipe);
        });
    }

    public void delete(Recipe recipe){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            recipeDao.delete(recipe);
        });
    }


    public LiveData<List<Recipe>> getAllRecipes(){
        return allRecipes;
    }

    public Recipe getByRecipeId(Integer recipeid) {
        Callable c = () -> {
            Recipe recipe = recipeDao.getByRecipeId(recipeid);
            return recipe;
        };
        Future<Recipe> future = UserRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            recipe = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    public Recipe findRecipeByUserId(Integer userrecipeid) {
        Callable c = () -> {
            Recipe recipe = recipeDao.getByRecipeId(userrecipeid);
            return recipe;
        };
        Future<Recipe> future = UserRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            recipe = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    public LiveData<List<Recipe>> findByTitle(String title) {
        return recipeDao.findByTitle(title);
    }

    public LiveData<List<Recipe>> getRecipesByUserId(Integer userrecipeid) {
        return recipeDao.getRecipesByUserId(userrecipeid);
    }


    // TODO: Delete Recipe
    public void deleteRecipe(Recipe recipe){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            recipeDao.delete(recipe);
        });
    }

    // TODO: Update a Recipe
    public void updateRecipe(Recipe recipe) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipeDao.updateRecipe(recipe);
        });
    }
}
