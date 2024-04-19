package com.example.arpshoplist.login;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arpshoplist.UserRoomDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class UserRepository {
    private UserRoomDatabase db;

    private UserDao userDao;

    private LiveData<List<User>> allUsers;

    private User user;

    public UserRepository(Application application) {
        db = UserRoomDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.findAll();

    }

    public void insert(User user){
        UserRoomDatabase.databaseWriteExecutor.execute(  () -> {
            userDao.insert(user);
        });
    }

    public void update(User user){
        UserRoomDatabase.databaseWriteExecutor.execute(  () -> {
            userDao.update(user);
        });
    }

    public void delete(User user){
        UserRoomDatabase.databaseWriteExecutor.execute(  () -> {
            userDao.delete(user);
        });
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

    public User findById(int userid){
        Callable c = () -> {
            User user = userDao.findById(userid);
            return user;
        };
        Future<User> future = UserRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            user = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    public LiveData<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

}
