package com.mwb.digitalstorage.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


@Entity(tableName = "storage")
public class Storage implements com.mwb.digitalstorage.model.Entity
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


    public Storage(@NonNull String name, @NonNull String location, String imgPath)
    {
        this.name = name;
        this.location = location;
        this.imgPath = imgPath;
    }

    @Override
    public String getName() { return name; }
    @Override
    public String getImgPath() { return imgPath; }
    @NonNull
    public String getLocation(){ return location; }
}