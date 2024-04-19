package com.example.arpshoplist.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.arpshoplist.UserRoomDatabase;
import com.example.arpshoplist.login.User;
import com.example.arpshoplist.login.UserRepository;
import com.example.arpshoplist.ingredients.Ingredients;
import com.example.arpshoplist.ingredients.IngredientsRepository;
import com.example.arpshoplist.recipe.Recipe;
import com.example.arpshoplist.recipe.RecipeIngredient;
import com.example.arpshoplist.recipe.RecipeIngredientDao;
import com.example.arpshoplist.recipe.RecipeIngredientRepository;
import com.example.arpshoplist.recipe.RecipeRepository;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListItem;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInListRepository;
import com.example.arpshoplist.shoppinglist.ShoppingListItemRepository;
import com.example.arpshoplist.shoppinglist.ShoppingListRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private RecipeRepository recipeRepository;
    private IngredientsRepository ingredientsRepository;
    private RecipeIngredientRepository recipeIngredientRepository;
    private ShoppingListItemRepository shoppingListItemRepository;
    private ShoppingListRepository shoppingListRepository;
    private ShoppingListItemInListRepository shoppingListItemInListRepository;

    private LiveData<List<User>> allUsers;
    public LiveData<List<Recipe>> allRecipes;
    public LiveData<List<Ingredients>> getAllIngredients;
    public LiveData<List<RecipeIngredient>> getAllIngredientsForRecipe;
    public LiveData<List<ShoppingListItem>> getAllShoppingListItems;
    public LiveData<List<ShoppingList>> getAllShoppingList;
    public LiveData<List<ShoppingListItemInList>> getAllShopListItemsInList;
    public MainViewModel(@NonNull Application application) {

        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();

        recipeRepository = new RecipeRepository(application);
        allRecipes = recipeRepository.getAllRecipes();

        ingredientsRepository = new IngredientsRepository(application);
        getAllIngredients = ingredientsRepository.getAllIngredients();

        recipeIngredientRepository = new RecipeIngredientRepository(application);
        getAllIngredientsForRecipe = recipeIngredientRepository.getAllIngredientsForRecipe();

        shoppingListItemRepository = new ShoppingListItemRepository(application);
        getAllShoppingListItems = shoppingListItemRepository.getAllShoppingListItems();

        shoppingListRepository = new ShoppingListRepository((application));
        getAllShoppingList = shoppingListRepository.getAllShoppingList();

        shoppingListItemInListRepository = new ShoppingListItemInListRepository(application);
        getAllShopListItemsInList = shoppingListItemInListRepository.getAllShopListItemsInList();
    }
    // TODO: Implement the ViewModel

    public void insert(User user) { userRepository.insert(user);}

    public void update(User user) { userRepository.update(user);}

    public void delete(User user) { userRepository.delete(user);}

    public User findById(Integer userid) {
        User user = userRepository.findById(userid);
        return user;
    }

    public LiveData<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public LiveData<List<User>> getAllUsers(){
        userRepository.getAllUsers();
        return allUsers;
    }

    // TODO: Implement the IngredientRepository
    public Long insert(Ingredients ingredient) {
        Callable<Long> insertCallable = () -> Long.valueOf(ingredientsRepository.insert(ingredient));
        try {
            return UserRoomDatabase.databaseWriteExecutor.submit(insertCallable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public void update(Ingredients ingredients) { ingredientsRepository.update(ingredients);}

    public void delete(Ingredients ingredients) { ingredientsRepository.delete(ingredients);}

    public Ingredients getIngredientById(Integer ingredientid) {
        Ingredients ingredients = ingredientsRepository.getIngredientById(ingredientid);
        return ingredients;
    }

    public LiveData<List<Ingredients>> getAllIngredients(){
        ingredientsRepository.getAllIngredients();
        return getAllIngredients;
    }

    public LiveData<List<Ingredients>> getIngredient(String ingredientname) {
        return ingredientsRepository.getIngredient(ingredientname);
    }

    // TODO: Implement the RecipeRepository
    public Long insert(Recipe recipe) {
        Callable<Long> insertCallable = () -> Long.valueOf(recipeRepository.insert(recipe));
        try {
            return UserRoomDatabase.databaseWriteExecutor.submit(insertCallable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public void update(Recipe recipe) { recipeRepository.update(recipe);}

    public void delete(Recipe recipe) { recipeRepository.delete(recipe);}

    public Recipe getByRecipeId(Integer recipeid) {
        Recipe recipe = recipeRepository.getByRecipeId(recipeid);
        return recipe;
    }

    public LiveData<List<Recipe>> findByTitle(String title) {
        return recipeRepository.findByTitle(title);
    }

    public LiveData<List<Recipe>> getAllRecipes(){
        recipeRepository.getAllRecipes();
        return allRecipes;
    }

    public Recipe findRecipeByUserId(Integer userrecipeid) {
        Recipe recipe = recipeRepository.findRecipeByUserId(userrecipeid);
        return recipe;
    }


    // TODO: Implement the RecipeIngredientRepository
    public Long insert(RecipeIngredient recipeIngredient) {
        Callable<Long> insertCallable = () -> Long.valueOf(recipeIngredientRepository.insert(recipeIngredient));
        try {
            return UserRoomDatabase.databaseWriteExecutor.submit(insertCallable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1L;
        }
    }
    public void update(RecipeIngredient recipeIngredient) {recipeIngredientRepository.update(recipeIngredient);}
    public void delete(RecipeIngredient recipeIngredient) {recipeIngredientRepository.delete(recipeIngredient);}

    public LiveData<List<RecipeIngredient>> getAllIngredientsForRecipe(){
        return recipeIngredientRepository.getAllIngredientsForRecipe();
    }
    public LiveData<List<RecipeIngredient>> findByQuantity(String quantity) {
        return recipeIngredientRepository.findByQuantity(quantity);
    }
    public RecipeIngredient getIngredientsForRecipeId(Integer recipeingredientid){
        RecipeIngredient recipeIngredient = recipeIngredientRepository.getIngredientsForRecipeId(recipeingredientid);
        return recipeIngredient;
    }

    public RecipeIngredient findRecipeByRecipeTableId(Integer recipetableid){
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findRecipeByRecipeTableId(recipetableid);
        return recipeIngredient;
    }
    public RecipeIngredient findRecipeByIngredientTableId(Integer ingredienttableid){
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findRecipeByIngredientTableId(ingredienttableid);
        return recipeIngredient;
    }

    // TODO: Implement the ShoppingListItemRepository
    public void insert(ShoppingListItem shoppingListItem) {shoppingListItemRepository.insert(shoppingListItem);}
    public void update(ShoppingListItem shoppingListItem) {shoppingListItemRepository.update(shoppingListItem);}
    public void delete(ShoppingListItem shoppingListItem) {shoppingListItemRepository.delete(shoppingListItem);}

    public LiveData<List<ShoppingListItem>> getAllShoppingListItems(){
        return shoppingListItemRepository.getAllShoppingListItems();
    }
    public LiveData<List<ShoppingListItem>> findByItemName(String itemname){
        return shoppingListItemRepository.findByItemName(itemname);
    }
    public LiveData<List<ShoppingListItem>> findByIconName(String iconname){
        return shoppingListItemRepository.findByIconName(iconname);
    }
    public LiveData<List<ShoppingListItem>> findByIconImage(String iconimage){
        return shoppingListItemRepository.findByIconImage(iconimage);
    }
    public ShoppingListItem getShoppingListItemByItemId(Integer itemid){
        ShoppingListItem shoppingListItem = shoppingListItemRepository.getShoppingListItemByItemId(itemid);
        return shoppingListItem;
    }

    public LiveData<ShoppingListItem> getThisLiveShoppingListItemByItemId(Integer itemId) {
        return shoppingListItemRepository.getThisLiveShoppingListItemByItemId(itemId);
    }

    // TODO: Implement the ShoppingListRepository
    public long insert(ShoppingList shoppingList) {
        Callable<Long> insertCallable = () -> shoppingListRepository.insert(shoppingList);
        try {
            return UserRoomDatabase.databaseWriteExecutor.submit(insertCallable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1L;
        }
    }
    public void update(ShoppingList shoppingList) {shoppingListRepository.update(shoppingList);}
    public void delete(ShoppingList shoppingList) {shoppingListRepository.delete(shoppingList);}

    public LiveData<List<ShoppingList>> getAllShoppingList(){
        return shoppingListRepository.getAllShoppingList();
    }
    public LiveData<ShoppingList> getShoppingListById(Integer shoppinglistid){
        return shoppingListRepository.getShoppingListById(shoppinglistid);
    }

    public LiveData<List<ShoppingList>> getListByUserId(Integer usertableid){
        return shoppingListRepository.getListByUserId(usertableid);
    }
    public LiveData<List<ShoppingList>> getListByTimeStamp(Date timestamp){
        return shoppingListRepository.getListByTimeStamp(timestamp);
    }
    public LiveData<List<ShoppingList>> getShoppingListByTitle(String title){
        return shoppingListRepository.getShoppingListByTitle(title);
    }

    // TODO: Implement the ShoppingListItemInListRepository
    public void insert(ShoppingListItemInList shoppingListItemInList) {shoppingListItemInListRepository.insert(shoppingListItemInList);}
    public void update(ShoppingListItemInList shoppingListItemInList) {shoppingListItemInListRepository.update(shoppingListItemInList);}
    public void delete(ShoppingListItemInList shoppingListItemInList) {shoppingListItemInListRepository.delete(shoppingListItemInList);}

    public LiveData<List<ShoppingListItemInList>> getAllShopListItemsInList(){
        return shoppingListItemInListRepository.getAllShopListItemsInList();
    }
    public LiveData<List<ShoppingListItemInList>> getShopListItemsInListById(Integer iteminlistid){
        return shoppingListItemInListRepository.getShopListItemsInListById(iteminlistid);
    }
    public LiveData<List<ShoppingListItemInList>> getShopListItemsInListByShopListTableId(Integer shoplisttableid){
        return shoppingListItemInListRepository.getShopListItemsInListByShopListTableId(shoplisttableid);
    }
    public LiveData<List<ShoppingListItemInList>> getShopListItemsInListByShopListItemTableId(Integer shoplistitemtableid){
        return shoppingListItemInListRepository.getShopListItemsInListByShopListItemTableId(shoplistitemtableid);
    }

    public void updateShoppingListItemInList(ShoppingListItemInList itemInList) {
        shoppingListItemInListRepository.update(itemInList);
    }


    // TODO: Fetching recipe retrieval methods
    public LiveData<List<Ingredients>> getIngredientsForRecipe(Integer recipeId) {
        return ingredientsRepository.findIngredientsForRecipe(recipeId);
    }
    public LiveData<List<RecipeIngredient>> getQuantityForRecipe(Integer recipetableid) {
        return recipeIngredientRepository.findRecipeIngredientsForRecipe(recipetableid);
    }

    public LiveData<List<Recipe>> getRecipesByUserId(Integer userrecipeid) {
        return recipeRepository.getRecipesByUserId(userrecipeid);
    }


    // TODO: Delete a Recipe
    public void deleteRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    // TODO: Update a Recipe
    public void updateRecipe(Recipe recipe, List<Ingredients> ingredients, List<RecipeIngredient> recipeIngredients) {
        recipeRepository.updateRecipe(recipe);
        ingredientsRepository.updateIngredients(ingredients);
        recipeIngredientRepository.updateRecipeIngredients(recipeIngredients);
    }
}