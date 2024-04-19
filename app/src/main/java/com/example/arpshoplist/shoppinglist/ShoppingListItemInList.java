package com.example.arpshoplist.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

import java.io.Serializable;

@Entity(tableName = "SHOPPINGLISTITEMINLIST",
        foreignKeys = {
                @ForeignKey(entity = ShoppingList.class,
                        parentColumns = "SHOPPINGLISTID",
                        childColumns = "SHOPLISTTABLEID",
                        onDelete = CASCADE),
                @ForeignKey(entity = ShoppingListItem.class,
                        parentColumns = "ITEMID",
                        childColumns = "SHOPLISTITEMTABLEID",
                        onDelete = CASCADE)
        },
        indices = {
                @Index(value = "SHOPLISTTABLEID"),
                @Index(value = "SHOPLISTITEMTABLEID")
        })
public class ShoppingListItemInList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ITEMINLISTID")
    private Integer iteminlistid;

    @ColumnInfo(name = "SHOPLISTTABLEID")
    private Integer shoplisttableid;

    @ColumnInfo(name = "SHOPLISTITEMTABLEID")
    private Integer shoplistitemtableid;

    public ShoppingListItemInList(Integer shoplisttableid, Integer shoplistitemtableid) {
        this.shoplisttableid = shoplisttableid;
        this.shoplistitemtableid = shoplistitemtableid;
    }

    @NonNull
    public Integer getIteminlistid() {
        return iteminlistid;
    }

    public void setIteminlistid(@NonNull Integer iteminlistid) {
        this.iteminlistid = iteminlistid;
    }

    public Integer getShoplisttableid() {
        return shoplisttableid;
    }

    public void setShoplisttableid(Integer shoplisttableid) {
        this.shoplisttableid = shoplisttableid;
    }

    public Integer getShoplistitemtableid() {
        return shoplistitemtableid;
    }

    public void setShoplistitemtableid(Integer shoplistitemtableid) {
        this.shoplistitemtableid = shoplistitemtableid;
    }
}
