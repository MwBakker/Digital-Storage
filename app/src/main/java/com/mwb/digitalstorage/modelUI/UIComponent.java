package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.ComponentOverViewActivity;
import androidx.databinding.ObservableField;


public class UIComponent implements UIEntity
{
    private long id;
    private long componentCategoryID;
    public long rackID;
    private String imgPath;

    public ObservableField<String> nameObsv = new ObservableField<>();
    public ObservableField<String> codeObsv = new ObservableField<>();
    public ObservableField<String> categoryNameObsv = new ObservableField<>();
    public ObservableField<String> countObsv = new ObservableField<>();
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();
    public ObservableField<Boolean> isEditObsv = new ObservableField<>();


    public UIComponent(long id, long rackID, long componentCategoryID, String componentCategoryName,
                        String name, String code, String imgPath, int count)
    {
        this.id = id;
        this.rackID = rackID;
        this.componentCategoryID = componentCategoryID;
        this.categoryNameObsv.set(componentCategoryName);
        this.nameObsv.set(name);
        this.codeObsv.set(code);
        this.imgPath = imgPath;
        this.countObsv.set(Integer.toString(count));
    }


    @Override
    public long getId() { return id; }

    public long getRackID() { return rackID; }

    public long getComponentCatID() { return componentCategoryID; }

    @Override
    public String getName() { return nameObsv.get(); }

    @Override
    public String getClassName() { return "Component"; }

    @Override
    public Class getBelongingOverViewActivity()
    {
        return ComponentOverViewActivity.class;
    }

    @Override
    public String getForeignKeyname() { return null; }

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