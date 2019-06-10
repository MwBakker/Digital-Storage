package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.RackOverViewActivity;
import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;


public class UIStorage extends BaseObservable implements UIEntity
{
    public long id;
    private String name;
    private String location;
    private String imgPath;

    public ObservableField<Boolean> allFieldsSetObsv = new ObservableField<>();
    public ObservableField<Integer> amountOfRacksObsv = new ObservableField<>();
    public ObservableField<Integer> amountOfComponentsObsv = new ObservableField<>();

    public ObservableField<Boolean> isEditObsv = new ObservableField<>(false);
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();


    public UIStorage(long id, String name, String location, String imgPath)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.imgPath = imgPath;
    }

    @Override
    public long getId() { return id; }

    @Override
    public String getName() { return name; }

    public void setName(String name)
    {
        this.name = name;
        setAllFieldsSet();
    }

    public String getLocation() { return location; }

    public void setLocation(String location)
    {
        this.location = location;
        setAllFieldsSet();
    }

    private void setAllFieldsSet()
    {
        if (name != null && location != null)
        {
            allFieldsSetObsv.set(name.length() > 1 && location.length() > 1);
        }
        else { allFieldsSetObsv.set(false); }
    }

    @Override
    public String getForeignKeyName() { return null; }

    @Override
    public String getSecondaryForeignKeyName() { return null; }

    @Override
    public String getClassName() { return "Storage"; }

    @Override
    public Class getBelongingOverViewActivity() { return RackOverViewActivity.class; }

    @Override
    public String getImgPath() { return imgPath; }

    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

    @Override
    public Bitmap getImg() { return imgObsv.get(); }

    @Override
    public void setImg(Bitmap img) { imgObsv.set(img); }

    @Override
    public void removeImg()
    {
        imgObsv = null;
        imgPath = null;
    }

    public void setAmountOfRacks(int amountOfRacks)  { amountOfRacksObsv.set(amountOfRacks); }

    public String getAmountOfRacksString() { return amountOfRacksObsv.get() + " Racks"; }

    public void setAmountOfComponents(int amountOfComponents) { amountOfComponentsObsv.set(amountOfComponents); }

    public String getAmountOfComponents() { return amountOfComponentsObsv.get() + " Components"; }

    @Override
    public String getCount() { return " "; }

    @Override
    public boolean isComponent() { return false; }









}