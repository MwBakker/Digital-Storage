package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import androidx.databinding.ObservableField;


public class UITool implements UIEntity
{
    private long id;

    private ObservableField<String> name;
    private ObservableField<String> location;
    private ObservableField<Boolean> availibility;


    public UITool(long id, String name, String location, boolean availibility)
    {
        this.id = id;
        this.name.set(name);
        this.location.set(location);
        this.availibility.set(availibility);
    }


    @Override
    public long getId() { return id; }

    @Override
    public String getName() { return null; }

    @Override
    public String getForeignKeyName() { return null; }

    @Override
    public String getSecondaryForeignKeyName() { return null; }

    @Override
    public String getClassName() { return null; }

    @Override
    public Class getBelongingOverViewActivity() { return null; }

    @Override
    public void setImg(Bitmap img) { }

    @Override
    public String getImgPath() { return null; }

    @Override
    public Bitmap getImg() { return null; }

    @Override
    public int getAmount() { return 0; }
}
