package com.example.arpshoplist.shoppinglist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arpshoplist.UserRoomDatabase;
import com.example.arpshoplist.login.User;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ShoppingListRepository {
    private UserRoomDatabase db;
    private ShoppingListDao shoppingListDao;
    private LiveData<List<ShoppingList>> getAllShoppingList;
    private ShoppingList shoppingList;

    public ShoppingListRepository(Application application){
        db = UserRoomDatabase.getDatabase(application);
        shoppingListDao = db.shoppingListDao();
        getAllShoppingList = shoppingListDao.getAllShoppingList();
    }

    public long insert(ShoppingList shoppingList){
        Callable<Long> insertCallable = () -> shoppingListDao.insert(shoppingList);
        try {
            return UserRoomDatabase.databaseWriteExecutor.submit(insertCallable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return -1L;
    }
    public void update(ShoppingList shoppingList){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            shoppingListDao.update(shoppingList);
        });
    }
    public void delete(ShoppingList shoppingList){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            shoppingListDao.delete(shoppingList);
        });
    }

    public LiveData<List<ShoppingList>> getAllShoppingList(){
        return getAllShoppingList;
    }
    public LiveData<ShoppingList> getShoppingListById(Integer shoppinglistid){
        return shoppingListDao.getShoppingListById(shoppinglistid);
    }

    public LiveData<List<ShoppingList>> getListByUserId(Integer usertableid){
        return shoppingListDao.getListByUserId(usertableid);
    }
    public LiveData<List<ShoppingList>> getListByTimeStamp(Date timestamp){
        return shoppingListDao.getListByTimeStamp(timestamp);
    }
    public LiveData<List<ShoppingList>> getShoppingListByTitle(String title){
        return shoppingListDao.getShoppingListByTitle(title);
    }
}
