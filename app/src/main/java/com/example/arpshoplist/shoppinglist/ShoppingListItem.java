package com.example.arpshoplist.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "SHOPPINGLISTITEM")
public class ShoppingListItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ITEMID")
    private Integer itemid;

    @ColumnInfo(name = "ITEMNAME")
    private String itemname;

    @ColumnInfo(name = "ICONNAME")
    private String iconname;

    @ColumnInfo(name = "ICONIMAGE")
    private String iconimage;

    public ShoppingListItem(String itemname, String iconname, String iconimage) {
        this.itemname = itemname;
        this.iconname = iconname;
        this.iconimage = iconimage;
    }

    @NonNull
    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(@NonNull Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getIconname() {
        return iconname;
    }

    public void setIconname(String iconname) {
        this.iconname = iconname;
    }

    public String getIconimage() {
        return iconimage;
    }

    public void setIconimage(String iconimage) {
        this.iconimage = iconimage;
    }
}
