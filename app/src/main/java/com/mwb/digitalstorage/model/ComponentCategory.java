package com.mwb.digitalstorage.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "component_category", indices = {@Index("id")})
public class ComponentCategory
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "component_amount")
    private int amountOfComponents;


    public ComponentCategory(String name, int amountOfComponents)
    {
        this.name = name;
        this.amountOfComponents = amountOfComponents;
    }

    //
    //  get the name + amount of categories at one string
    //
    public String getName() { return this.name; }

    public int getAmountOfComponents() { return this.amountOfComponents; }
}