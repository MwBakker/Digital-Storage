package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.RackOverViewActivity;
import androidx.databinding.ObservableField;


public class UIStorage implements UIEntity
{
    public long id;
    private int amountOfRacks;
    private int amountOfComponents;
    private String imgPath;

    public ObservableField<String> nameObsv = new ObservableField<>("");
    public ObservableField<String> locationObsv = new ObservableField<>("");
    public ObservableField<Boolean> isEditObsv = new ObservableField<>(false);
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();
    public ObservableField<Integer> rackAmountObsv = new ObservableField<>();


    public UIStorage(long id, String name, String location, String imgPath)
    {
        this.id = id;
        this.nameObsv.set(name);
        this.locationObsv.set(location);
        this.imgPath = imgPath;
    }

    @Override
    public long getId() { return id; }

    @Override
    public String getName() { return nameObsv.get(); }

    @Override
    public String getForeignKeyName() { return null; }

    @Override
    public String getSecondaryForeignKeyName() { return null; }

    @Override
    public String getClassName() { return "Storage"; }

    @Override
    public Class getBelongingOverViewActivity() { return RackOverViewActivity.class; }

    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

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

    @Override
    public String getImgPath() { return imgPath; }

    public void setAmountOfRacks(int amountOfRacks)  { this.amountOfRacks = amountOfRacks; }

    public String getAmountOfRacks() { return amountOfRacks + " Racks"; }

    public void setAmountOfComponents(int amountOfComponents)  { this.amountOfComponents = amountOfComponents; }

    public String getAmountOfComponents() { return amountOfComponents + " Components"; }

    @Override
    public String getCount() { return " "; }

    @Override
    public boolean isComponent() { return false; }









}