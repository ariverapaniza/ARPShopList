package com.example.arpshoplist.recipe;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

import com.example.arpshoplist.ingredients.Ingredients;

import java.io.Serializable;

@Entity(tableName = "RECIPEINGREDIENT",
        foreignKeys = {
                @ForeignKey(entity = Recipe.class,
                        parentColumns = "RECIPEID",
                        childColumns = "RECIPETABLEID",
                        onDelete = CASCADE),
                @ForeignKey(entity = Ingredients.class,
                        parentColumns = "INGREDIENTID",
                        childColumns = "INGREDIENTTABLEID",
                        onDelete = CASCADE)
        },
        indices = {
                @Index(value = "RECIPETABLEID"),
                @Index(value = "INGREDIENTTABLEID")
        })
public class RecipeIngredient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "RECIPEINGREDIENTID")
    private Integer recipeingredientid;

    @ColumnInfo(name = "RECIPETABLEID")
    private Integer recipetableid;

    @ColumnInfo(name = "INGREDIENTTABLEID")
    private Integer ingredienttableid;

    @ColumnInfo(name = "QUANTITY")
    private String quantity;

    public RecipeIngredient(Integer recipetableid, Integer ingredienttableid, String quantity) {
        this.recipetableid = recipetableid;
        this.ingredienttableid = ingredienttableid;
        this.quantity = quantity;
    }

    @NonNull
    public Integer getRecipeingredientid() {
        return recipeingredientid;
    }

    public void setRecipeingredientid(@NonNull Integer recipeingredientid) {
        this.recipeingredientid = recipeingredientid;
    }

    public Integer getRecipetableid() {
        return recipetableid;
    }

    public void setRecipetableid(Integer recipetableid) {
        this.recipetableid = recipetableid;
    }

    public Integer getIngredienttableid() {
        return ingredienttableid;
    }

    public void setIngredienttableid(Integer ingredienttableid) {
        this.ingredienttableid = ingredienttableid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
