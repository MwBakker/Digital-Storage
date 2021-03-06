package com.mwb.digitalstorage.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "component", indices = {@Index("id"), @Index("component_category_id"), @Index("rack_id") },
foreignKeys = {
@ForeignKey(
        entity = Rack.class,
        parentColumns = {"id"},
        childColumns = {"rack_id"},
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
),
@ForeignKey(
        entity = ComponentCategory.class,
        parentColumns = {"id"},
        childColumns = {"component_category_id"},
        onUpdate = ForeignKey.CASCADE
)}
)

public class Component implements com.mwb.digitalstorage.model.Entity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long componentID;

    @ColumnInfo(name="rack_id")
    public long rackID;

    @ColumnInfo(name="component_category_id")
    public long componentCategoryID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "img_path")
    private String imgPath;

    public Component(long rackID, long componentCategoryID, String name,
                     String code, String imgPath)
    {
        this.rackID = rackID;
        this.componentCategoryID = componentCategoryID;
        this.name = name;
        this.code = code;
        this.imgPath = imgPath;
    }

    @Override
    public String getName() { return this.name; }

    @Override
    public String getImgPath() { return this.imgPath; }

    public String getCode(){ return this.code; }
}
