package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class UICompany
{
    private long companyID;
    private String companyName;
    private String companyLoc;
    private Bitmap companyImg;


    public UICompany(long companyID, String companyName, String companyLoc, String imgPath)
    {
        this.companyID = companyID;
        this.companyName = companyName;
        this.companyLoc = companyLoc;
        generateCompanyImg(imgPath);
    }

    public String getCompanyName(){ return companyName; }
    public String getCompanyLoc(){ return companyLoc; }
    public Bitmap getItemBitmap() { return companyImg; }

    //
    //  generates a bitmap from the location path to the image
    //
    private void generateCompanyImg(String imgPath)
    {
        if (imgPath != null) { companyImg = BitmapFactory.decodeFile(imgPath); }
        else { companyImg = null; }
    }
}