package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;

public interface UIEntity
{
    long getId();

    String getName();

    String getForeignKeyName();

    String getSecondaryForeignKeyName();

    String getClassName();

    Class getBelongingOverViewActivity();

    void setImg(Bitmap img);

    String getImgPath();

    Bitmap getImg();

    void removeImg();

    int getCount();

    boolean isComponent();
}