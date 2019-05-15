package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import androidx.databinding.ObservableField;


public class UICompany
{
    private String imgPath;

    public ObservableField<String> nameObsv = new ObservableField<>();
    public ObservableField<String> locationObsv = new ObservableField<>();
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();
    public ObservableField<Boolean> isEdit = new ObservableField<>();


    public UICompany(long companyID, String companyName, String companyLoc, String imgPath)
    {
        this.nameObsv.set(companyName);
        this.locationObsv.set(companyLoc);
        this.imgPath = imgPath;
    }

    //
    //  sets the imgPath
    //
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

    public String getImgPath() { return imgPath; }

    public void removeImg()
    {
        imgObsv = null;
        imgPath = null;
    }
}