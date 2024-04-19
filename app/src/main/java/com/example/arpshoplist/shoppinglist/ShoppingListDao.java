package com.example.arpshoplist.shoppinglist;

import android.view.inputmethod.InsertGesture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ShoppingList shoppingList);
    @Update
    void update(ShoppingList shoppingList);
    @Delete
    void delete(ShoppingList shoppingList);

    @Query("SELECT * FROM SHOPPINGLIST")
    LiveData<List<ShoppingList>> getAllShoppingList();

    @Query("SELECT * FROM SHOPPINGLIST WHERE SHOPPINGLISTID = :shoppinglistid")
    LiveData<ShoppingList> getShoppingListById(Integer shoppinglistid);

    @Query("SELECT * FROM SHOPPINGLIST WHERE USERTABLEID = :usertableid")
    LiveData<List<ShoppingList>> getListByUserId(Integer usertableid);

    @Query("SELECT * FROM SHOPPINGLIST WHERE TIMESTAMP = :timestamp")
    LiveData<List<ShoppingList>> getListByTimeStamp(Date timestamp);

    @Query("SELECT * FROM SHOPPINGLIST WHERE TITLE = :title")
    LiveData<List<ShoppingList>> getShoppingListByTitle(String title);


}
