package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import androidx.databinding.ObservableField;


public class UIStorage implements UIEntity
{
    public long id;

    private String imgPath;
    private Bitmap img;

    public ObservableField<String> nameObsv = new ObservableField<>("");
    public ObservableField<String> locationObsv = new ObservableField<>("");
    public ObservableField<Boolean> isEditObsv = new ObservableField<>(false);


    public UIStorage(long id, String name, String location, String imgPath)
    {
        this.id = id;
        this.nameObsv.set(name);
        this.locationObsv.set(location);
        this.imgPath = imgPath;
    }

    @Override
    public long getId() { return id; }
    @Override
    public String getName() { return nameObsv.get(); }
    @Override
    public String getParentKeyName() { return null; }
    @Override
    public Bitmap getImg() { return img; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    public String getImgPath() { return imgPath; }
    @Override
    public void removeImg()
    {
        img = null;
        imgPath = null;
    }
}