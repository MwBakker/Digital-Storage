package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
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
        this.imgPath = imgPath;
        this.count = Integer.toString(count);
    }



    public String getImgPath() { return imgPath; }
    public String getCount() { return count ; }


    @Override
    public long getId() { return id; }
    public long getRackID() { return rackID; }
    public long getComponentCatID() { return componentCatID; }
    @Override
    public String getName() { return name.get(); }
    public String getComponentCatName() { return componentCatName; }
    @Override
    public String getParentKeyName() { return null; }
    @Override
    public Bitmap getImg() { return componentImg; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    @Override
    public void removeImg()
    {
        componentImg = null;
        imgPath = null;
    }
}