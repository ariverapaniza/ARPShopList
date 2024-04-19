package com.example.arpshoplist.ingredients;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "INGREDIENTS")
public class Ingredients implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "INGREDIENTID")
    private Integer ingredientid;

    @ColumnInfo(name = "INGREDIENTNAME")
    private String ingredientname;

    @Ignore
    public Ingredients(){}

    public Ingredients(String ingredientname) {
        this.ingredientname = ingredientname;
    }

    @NonNull
    public Integer getIngredientid() {
        return ingredientid;
    }

    public void setIngredientid(@NonNull Integer ingredientid) {
        this.ingredientid = ingredientid;
    }

    public String getIngredientname() {
        return ingredientname;
    }

    public void setIngredientname(String ingredientname) {
        this.ingredientname = ingredientname;
    }
}
