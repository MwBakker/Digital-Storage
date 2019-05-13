package com.mwb.digitalstorage.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "tool")
public class Tool implements com.mwb.digitalstorage.model.Entity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "available")
    private boolean available;


    public Tool(@NonNull String name, @NonNull String location, String description, boolean available)
    {
        this.name = name;
        this.location = location;
        this.description = description;
        this.available = available;
    }

    @Override
    public String getName() { return name; }
    @Override
    public String getImgPath() { return null; }
    @NonNull
    public String getLocation(){ return location; }
    @NonNull
    public String getDescription(){ return description; }
    @NonNull
    public boolean isAvailable() { return available; }
}
