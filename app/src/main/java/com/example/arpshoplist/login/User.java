package com.example.arpshoplist.login;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity(tableName = "USER")
public class User {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "USERID")
    private Integer userid;

    @ColumnInfo(name = "USERNAME")
    private String username;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "EMAIL")
    private String email;

    @ColumnInfo(name = "PASSWORD")
    private String password;

    @Ignore
    public User(){
    }


    public User(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @NonNull
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(@NonNull Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
