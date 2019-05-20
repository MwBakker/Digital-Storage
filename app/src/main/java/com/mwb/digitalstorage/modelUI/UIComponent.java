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

    public ObservableField<String> nameObsv = new ObservableField<>();
    public ObservableField<String> codeObsv = new ObservableField<>();
    public ObservableField<String> count = new ObservableField<>();
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();
    public ObservableField<Boolean> isEditObsv = new ObservableField<>();



    public UIComponent(long id, long rackID, long componentCatID, String componentCatName,
                        String name, String code, String imgPath, int count)
    {
        this.id = id;
        this.rackID = rackID;
        this.componentCatID = componentCatID;
        this.nameObsv.set(name);
        this.componentCatName = componentCatName;
        this.codeObsv.set(code);
        this.imgPath = imgPath;
        this.count.set(Integer.toString(count));
    }


    @Override
    public long getId() { return id; }

    public long getRackID() { return rackID; }

    public long getComponentCatID() { return componentCatID; }

    @Override
    public String getName() { return nameObsv.get(); }

    @Override
    public String getClassName() { return "Component"; }

    public String getComponentCatName() { return componentCatName; }

    @Override
    public String getParentKeyName() { return null; }

    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

    @Override
    public String getImgPath() { return imgPath; }

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