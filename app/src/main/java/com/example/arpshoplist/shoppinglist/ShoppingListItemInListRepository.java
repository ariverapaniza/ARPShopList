package com.example.arpshoplist.shoppinglist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arpshoplist.UserRoomDatabase;

import java.util.List;

public class ShoppingListItemInListRepository {
    private UserRoomDatabase db;
    private ShoppingListItemInListDao shoppingListItemInListDao;
    private LiveData<List<ShoppingListItemInList>> getAllShopListItemsInList;
    private ShoppingListItemInList shoppingListItemInList;
    public ShoppingListItemInListRepository(Application application){
        db = UserRoomDatabase.getDatabase(application);
        shoppingListItemInListDao = db.shoppingListItemInListDao();
        getAllShopListItemsInList = shoppingListItemInListDao.getAllShopListItemsInList();
    }
    public void insert(ShoppingListItemInList shoppingListItemInList){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            shoppingListItemInListDao.insert(shoppingListItemInList);
        });
    }
    public void update(ShoppingListItemInList shoppingListItemInList){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            shoppingListItemInListDao.update(shoppingListItemInList);
        });
    }
    public void delete(ShoppingListItemInList shoppingListItemInList){
        UserRoomDatabase.databaseWriteExecutor.execute( () -> {
            shoppingListItemInListDao.delete(shoppingListItemInList);
        });
    }
    public LiveData<List<ShoppingListItemInList>> getAllShopListItemsInList(){
        return getAllShopListItemsInList;
    }
    public LiveData<List<ShoppingListItemInList>> getShopListItemsInListById(Integer iteminlistid){
        return shoppingListItemInListDao.getShopListItemsInListById(iteminlistid);
    }
    public LiveData<List<ShoppingListItemInList>> getShopListItemsInListByShopListTableId(Integer shoplisttableid){
        return shoppingListItemInListDao.getShopListItemsInListByShopListTableId(shoplisttableid);
    }
    public LiveData<List<ShoppingListItemInList>> getShopListItemsInListByShopListItemTableId(Integer shoplistitemtableid){
        return shoppingListItemInListDao.getShopListItemsInListByShopListItemTableId(shoplistitemtableid);
    }

    public void updateShoppingListItemInList(ShoppingListItemInList itemInList) {
        shoppingListItemInListDao.update(itemInList);
    }

}
