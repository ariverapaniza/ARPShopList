package com.example.arpshoplist.recipe;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arpshoplist.UserRoomDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RecipeIngredientRepository {
    private UserRoomDatabase db;
    private RecipeIngredientDao recipeIngredientDao;
    private LiveData<List<RecipeIngredient>> getAllIngredientsForRecipe;
    private RecipeIngredient recipeIngredient;

    public RecipeIngredientRepository(Application application){
        db = UserRoomDatabase.getDatabase(application);
        recipeIngredientDao = db.recipeIngredientDao();
        getAllIngredientsForRecipe = recipeIngredientDao.getAllIngredientsForRecipe();
    }

    public Long insert(RecipeIngredient recipeIngredient) {
        Callable<Long> insertCallable = () -> recipeIngredientDao.insert(recipeIngredient);
        try {
            return UserRoomDatabase.databaseWriteExecutor.submit(insertCallable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1L;
    }


    public void update(RecipeIngredient recipeIngredient){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            recipeIngredientDao.update(recipeIngredient);
        });
    }

    public void delete(RecipeIngredient recipeIngredient){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            recipeIngredientDao.delete(recipeIngredient);
        });
    }

    public LiveData<List<RecipeIngredient>> getAllIngredientsForRecipe(){
        return getAllIngredientsForRecipe;
    }

    public LiveData<List<RecipeIngredient>> findByQuantity(String quantity) {
        return recipeIngredientDao.findByQuantity(quantity);
    }

    public RecipeIngredient getIngredientsForRecipeId(Integer recipeingredientid) {
        Callable c = () -> {
            RecipeIngredient recipeIngredient = recipeIngredientDao.getIngredientsForRecipeId(recipeingredientid);
            return recipeIngredient;
        };
        Future<RecipeIngredient> future = UserRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            recipeIngredient = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return recipeIngredient;
    }

    public RecipeIngredient findRecipeByRecipeTableId(Integer recipetableid) {
        Callable c = () -> {
            RecipeIngredient recipeIngredient = recipeIngredientDao.findRecipeByRecipeTableId(recipetableid);
            return recipeIngredient;
        };
        Future<RecipeIngredient> future = UserRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            recipeIngredient = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return recipeIngredient;
    }

    public RecipeIngredient findRecipeByIngredientTableId(Integer ingredienttableid) {
        Callable c = () -> {
            RecipeIngredient recipeIngredient = recipeIngredientDao.findRecipeByIngredientTableId(ingredienttableid);
            return recipeIngredient;
        };
        Future<RecipeIngredient> future = UserRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            recipeIngredient = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return recipeIngredient;
    }

    // TODO: Fetching recipe retreival methods
    public LiveData<List<RecipeIngredient>> findRecipeIngredientsForRecipe(Integer recipetableid) {
        return recipeIngredientDao.findRecipeIngredientsForRecipe(recipetableid);
    }

    // TODO: Update a Recipe
    public void updateRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipeIngredientDao.updateRecipeIngredients(recipeIngredients);
        });
    }

}
