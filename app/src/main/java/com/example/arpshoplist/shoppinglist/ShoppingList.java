package com.example.arpshoplist.shoppinglist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

import com.example.arpshoplist.login.User;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "SHOPPINGLIST",
        foreignKeys = {

                @ForeignKey(entity = User.class,
                parentColumns = "USERID",
                childColumns = "USERTABLEID",
                onDelete = CASCADE)
        },
        indices = {

                @Index(value = "USERTABLEID")
        })
public class ShoppingList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "SHOPPINGLISTID")
    private Integer shoppinglistid;


    @ColumnInfo(name = "USERTABLEID")
    private Integer usertableid;

    @ColumnInfo(name = "TIMESTAMP")
    private Date timestamp;

    @ColumnInfo(name = "TITLE")
    private String title;

    public ShoppingList(Integer usertableid, Date timestamp, String title) {
        this.usertableid = usertableid;
        this.timestamp = timestamp;
        this.title = title;
    }

    @NonNull
    public Integer getShoppinglistid() {
        return shoppinglistid;
    }

    public void setShoppinglistid(@NonNull Integer shoppinglistid) {
        this.shoppinglistid = shoppinglistid;
    }


    public Integer getUsertableid() {
        return usertableid;
    }

    public void setUsertableid(Integer usertableid) {
        this.usertableid = usertableid;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
