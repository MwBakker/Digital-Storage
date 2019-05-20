package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import androidx.databinding.ObservableField;


public class UIRack implements UIEntity
{
    public long id;
    private String imgPath;

    public ObservableField<String> nameObsv = new ObservableField<>("");
    public ObservableField<Integer> componentCountObsv = new ObservableField<>();
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();
    public ObservableField<Boolean> isEditObsv = new ObservableField<>(false);


    public UIRack(long id, String name, String imgPath, int componentCount)
    {
        this.id = id;
        this.nameObsv.set(name);
        this.imgPath = imgPath;
        this.componentCountObsv.set(componentCount);
    }

    @Override
    public long getId() { return id; }

    @Override
    public String getName() { return nameObsv.get(); }

    @Override
    public String getClassName() { return "Rack"; }

    @Override
    public String getParentKeyName() { return null; }

    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

    @Override
    public String getImgPath() { return  imgPath; }

    @Override
    public void setImg(Bitmap img) { imgObsv.set(img); }

    @Override
    public Bitmap getImg() { return imgObsv.get(); }

    @Override
    public void removeImg()
    {
        imgObsv = null;
        imgPath = null;
    }
}