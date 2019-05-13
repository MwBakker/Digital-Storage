package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import androidx.databinding.ObservableField;


public class UIRack implements UIEntity
{
    public long id;
    private String imgPath;

    public ObservableField<String> rackName = new ObservableField<>("");
    public ObservableField<Integer> componentCount = new ObservableField<>();
    public ObservableField<Bitmap> rackImg = new ObservableField<>();
    public ObservableField<Boolean> isEdit = new ObservableField<>(false);


    public UIRack(long id, String name, String imgPath, int componentCount)
    {
        this.id = id;
        this.rackName.set(name);
        generateImg(imgPath);
        this.componentCount.set(componentCount);
    }

    public String getImgPath() { return  imgPath; }
    @Override
    public long getId() { return id; }
    @Override
    public String getName() { return rackName.get(); }
    @Override
    public String getParentKeyName() { return null; }
    @Override
    public Bitmap getImg() { return null; }
    @Override
    public void setImgPath(String imgPath) { generateImg(imgPath); }
    @Override
    public void removeImg()
    {
        rackImg = null;
        imgPath = null;
    }

    //
    //  generates an image from the image path
    //
    private void generateImg(String imgPath)
    {
        this.imgPath = imgPath;
        if (imgPath != null) { rackImg.set(BitmapFactory.decodeFile(imgPath)); }
        else { rackImg = null; }
    }
}