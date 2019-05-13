package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.databinding.ObservableField;


public class UIStorage implements UIEntity
{
    public long id;
    private Bitmap img;

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> location = new ObservableField<>("");
    public ObservableField<Boolean> isEdit = new ObservableField<>(false);


    public UIStorage(long id, String name, String location, String imgPath)
    {
        this.id = id;
        this.name.set(name);
        this.location.set(location);
        generateImg(imgPath);
    }

    @Override
    public long getId() { return id; }
    @Override
    public String getName() { return name.get(); }
    @Override
    public String getParentKeyName() { return null; }
    @Override
    public Bitmap getImg() { return img; }
    @Override
    public void setImgPath(String imgPath) { }
    @Override
    public void removeImg()
    {
        //rackImg = null;
        //imgPath = null;
    }
    //
    //  generates an image from the image path
    //
    private void generateImg(String imgPath)
    {
        if (imgPath != null) { img = BitmapFactory.decodeFile(imgPath); }
        else { img = null; }
    }
}