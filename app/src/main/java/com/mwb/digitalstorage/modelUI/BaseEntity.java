package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;

import androidx.databinding.ObservableField;

public abstract class BaseEntity
{
    public ObservableField<Boolean> allFieldsSetObsv = new ObservableField<>();
    public ObservableField<Boolean> isEditObsv = new ObservableField<>(false);
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();

    protected String imgPath;


    public String getClassName() { return getClassName().substring(1); }

    public Bitmap getImg() { return imgObsv.get(); }

    public void setImg(Bitmap img) { imgObsv.set(img); }

    public void removeImg()
    {
        imgObsv = null;
        imgPath = null;
    }

    public String getImgPath() { return imgPath; }

    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
}
