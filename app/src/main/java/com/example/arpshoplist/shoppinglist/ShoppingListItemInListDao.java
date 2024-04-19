package com.example.arpshoplist.shoppinglist;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingListItemInListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShoppingListItemInList shoppingListItemInList);
    @Update
    void update(ShoppingListItemInList shoppingListItemInList);
    @Delete
    void delete(ShoppingListItemInList shoppingListItemInList);

    @Query("SELECT * FROM SHOPPINGLISTITEMINLIST")
    LiveData<List<ShoppingListItemInList>> getAllShopListItemsInList();

    @Query("SELECT * FROM SHOPPINGLISTITEMINLIST WHERE ITEMINLISTID = :iteminlistid")
    LiveData<List<ShoppingListItemInList>> getShopListItemsInListById(Integer iteminlistid);

    @Query("SELECT * FROM SHOPPINGLISTITEMINLIST WHERE SHOPLISTTABLEID = :shoplisttableid")
    LiveData<List<ShoppingListItemInList>> getShopListItemsInListByShopListTableId(Integer shoplisttableid);

    @Query("SELECT * FROM SHOPPINGLISTITEMINLIST WHERE SHOPLISTITEMTABLEID = :shoplistitemtableid")
    LiveData<List<ShoppingListItemInList>> getShopListItemsInListByShopListItemTableId(Integer shoplistitemtableid);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateShoppingListItemInList(ShoppingListItemInList itemInList);


}
