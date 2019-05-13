package com.mwb.digitalstorage.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "rack", indices = { @Index("id"), @Index("storage_id") },
foreignKeys = {
        @ForeignKey(
                entity = Storage.class,
                parentColumns = {"id"},
                childColumns = {"storage_id"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )}
)


public class Rack implements com.mwb.digitalstorage.model.Entity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "storage_id")
    public long storageID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "img_path")
    private String imgPath;

    @ColumnInfo(name = "component_count")
    private int componentCount;


    public Rack(long storageID, String name, String imgPath, int componentCount)
    {
        this.storageID = storageID;
        this.name = name;
        this.imgPath = imgPath;
        this.componentCount = componentCount;
    }


    @Override
    public String getName() { return name; }
    @Override
    public String getImgPath() { return imgPath; }
    public long getStorageID() { return this.storageID; }
    public String getRackImgPath(){ return this.imgPath; }
    public int getComponentCount() {return this.componentCount; }
}