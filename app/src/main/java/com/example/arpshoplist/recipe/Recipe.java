package com.example.arpshoplist.recipe;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

import com.example.arpshoplist.login.User;

import java.io.Serializable;

@Entity(tableName = "RECIPE",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "USERID",
                childColumns = "USERRECIPEID",
                onDelete = CASCADE),
        indices = {@Index(value = {"USERRECIPEID"})})
public class Recipe implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "RECIPEID")
    private Integer recipeid;

    @ColumnInfo(name = "USERRECIPEID")
    private Integer userrecipeid;

    @ColumnInfo(name = "TITLE")
    private String title;

    @ColumnInfo(name = "DESCRIPTION")
    private String description;

    @Ignore
    public Recipe(){}

    public Recipe(Integer userrecipeid, String title, String description) {
        this.userrecipeid = userrecipeid;
        this.title = title;
        this.description = description;
    }

    @NonNull
    public Integer getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(@NonNull Integer recipeid) {
        this.recipeid = recipeid;
    }

    public Integer getUserrecipeid() {
        return userrecipeid;
    }

    public void setUserrecipeid(Integer userrecipeid) {
        this.userrecipeid = userrecipeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
