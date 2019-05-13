package com.mwb.digitalstorage.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "company")
public class Company
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "img_path")
    private String imgPath;


    public Company(String name, String location, String imgPath)
    {
        this.name = name;
        this.location = location;
        this.imgPath = imgPath;
    }

    public String getName(){ return name; }
    public String getLocation(){ return location; }
    public String getImgPath() { return imgPath; }
}
