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
public interface ShoppingListItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShoppingListItem shoppingListItem);

    @Update
    void update(ShoppingListItem shoppingListItem);

    @Delete
    void delete(ShoppingListItem shoppingListItem);

    @Query("SELECT * FROM SHOPPINGLISTITEM")
    LiveData<List<ShoppingListItem>> getAllShoppingListItems();

    @Query("SELECT * FROM SHOPPINGLISTITEM WHERE ITEMID = :itemid")
    ShoppingListItem getShoppingListItemByItemId(Integer itemid);

    @Query("SELECT * FROM SHOPPINGLISTITEM WHERE ITEMNAME = :itemname")
    LiveData<List<ShoppingListItem>> findByItemName(String itemname);

    @Query("SELECT * FROM SHOPPINGLISTITEM WHERE ITEMNAME = :iconname")
    LiveData<List<ShoppingListItem>> findByIconName(String iconname);

    @Query("SELECT * FROM SHOPPINGLISTITEM WHERE ITEMNAME = :iconimage")
    LiveData<List<ShoppingListItem>> findByIconImage(String iconimage);

    @Query("SELECT * FROM SHOPPINGLISTITEM WHERE ITEMID = :itemid")
    LiveData<ShoppingListItem> getThisLiveShoppingListItemByItemId(Integer itemid);

}
