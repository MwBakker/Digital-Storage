package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;

public interface UIEntity
{
    long getId();

    String getName();

    String getClassName();

    String getParentKeyName();

    String getImgPath();

    void setImg(Bitmap img);

    Bitmap getImg();

    void removeImg();
}