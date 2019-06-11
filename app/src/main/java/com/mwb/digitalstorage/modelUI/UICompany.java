package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import androidx.databinding.ObservableField;


public class UICompany
{
    private String imgPath;
    private int amountOfStorageUnits;
    private int amountOfRacks;
    private int amountOfComponents;

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

    //  sets the image path
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

    //  gets the image path
    public String getImgPath() { return imgPath; }

    // removes the image
    public void removeImg()
    {
        imgObsv = null;
        imgPath = null;
    }

    public int getAmountOfStorageUnits() { return amountOfStorageUnits; }

    public void setAmountOfStorages(int amountOfStorageUnits) { this.amountOfStorageUnits = amountOfStorageUnits; }

    public int getAmountOfRacks() { return amountOfRacks; }

    public void setAmountOfRacks(int amountOfRacks) { this.amountOfRacks = amountOfRacks; }

    public int getAmountOfComponents() { return amountOfComponents; }

    public void setAmountOfComponents(int amountOfComponents) { this.amountOfComponents = amountOfComponents; }
}