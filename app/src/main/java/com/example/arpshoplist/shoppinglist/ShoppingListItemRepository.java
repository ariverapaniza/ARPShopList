package com.example.arpshoplist.shoppinglist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arpshoplist.UserRoomDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ShoppingListItemRepository {
    private UserRoomDatabase db;
    private ShoppingListItemDao shoppingListItemDao;
    private LiveData<List<ShoppingListItem>> getAllShoppingListItems;
    private ShoppingListItem shoppingListItem;

    public ShoppingListItemRepository(Application application){
        db = UserRoomDatabase.getDatabase(application);
        shoppingListItemDao = db.shoppingListItemDao();
        getAllShoppingListItems = shoppingListItemDao.getAllShoppingListItems();
    }

    public void insert(ShoppingListItem shoppingListItem){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            shoppingListItemDao.insert(shoppingListItem);
        });
    }

    public void update(ShoppingListItem shoppingListItem){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            shoppingListItemDao.update(shoppingListItem);
        });
    }

    public void delete(ShoppingListItem shoppingListItem){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            shoppingListItemDao.delete(shoppingListItem);
        });
    }

    public LiveData<List<ShoppingListItem>> getAllShoppingListItems(){
        return getAllShoppingListItems;
    }

    public LiveData<List<ShoppingListItem>> findByItemName(String itemname) {
        return shoppingListItemDao.findByItemName(itemname);
    }

    public LiveData<List<ShoppingListItem>> findByIconName(String iconname) {
        return shoppingListItemDao.findByIconName(iconname);
    }

    public LiveData<List<ShoppingListItem>> findByIconImage(String iconimage) {
        return shoppingListItemDao.findByIconImage(iconimage);
    }

    public ShoppingListItem getShoppingListItemByItemId(Integer itemid) {
        Callable c = () -> {
            ShoppingListItem shoppingListItem1 = shoppingListItemDao.getShoppingListItemByItemId(itemid);
            return shoppingListItem;
        };
        Future<ShoppingListItem> future = UserRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            shoppingListItem = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return shoppingListItem;
    }

    public LiveData<ShoppingListItem> getThisLiveShoppingListItemByItemId(Integer itemId) {
        return shoppingListItemDao.getThisLiveShoppingListItemByItemId(itemId);
    }

}
