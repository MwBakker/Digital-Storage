package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;

import androidx.databinding.ObservableField;

public class UIComponent implements UIEntity
{
    private long id;
    private long componentCatID;
    public long rackID;
    private String componentCatName;
    private String imgPath;
    private Bitmap componentImg;
    private String count;

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> code = new ObservableField<>();
    public ObservableField<Boolean> isEdit = new ObservableField<>();


    public UIComponent(long id, long rackID, long componentCatID, String componentCatName,
                        String name, String code, String imgPath, int count)
    {
        this.id = id;
        this.rackID = rackID;
        this.componentCatID = componentCatID;
        this.name.set(name);
        this.componentCatName = componentCatName;
        this.code.set(code);
        generateImg(imgPath);
        this.count = Integer.toString(count);
    }

    public long getItemID() { return id; }
    public long getRackID() { return rackID; }
    public long getComponentCatID() { return componentCatID; }
    public String getComponentCatName() { return componentCatName; }
    public String getImgPath() { return imgPath; }
    public String getCount() { return count ; }

    //
    //  generates an image from the image path
    //
    private void generateImg(String imgPath)
    {
        this.imgPath = imgPath;
        if (imgPath != null) {
            File imgFile = new File(imgPath);
            componentImg = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        } else componentImg = null;
    }

    @Override
    public long getId() { return id; }
    @Override
    public String getName() { return name.get(); }
    @Override
    public String getParentKeyName() { return null; }
    @Override
    public Bitmap getImg() { return componentImg; }
    @Override
    public void setImgPath(String imgPath) { }
    @Override
    public void removeImg()
    {
        componentImg = null;
        imgPath = null;
    }
}