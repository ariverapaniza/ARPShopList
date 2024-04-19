package com.example.arpshoplist.login;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM USER")
    LiveData<List<User>> findAll();

    @Query("SELECT * FROM USER WHERE USERID = :userid")
    User findById(int userid);

    @Query("SELECT * FROM USER WHERE USERNAME = :username LIMIT 1")
    LiveData<User> findByUsername(String username);
}
