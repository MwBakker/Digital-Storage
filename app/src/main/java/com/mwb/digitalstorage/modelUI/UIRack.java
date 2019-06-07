package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.StorageOverViewActivity;
import androidx.databinding.ObservableField;


public class UIRack implements UIEntity
{
    public long id;
    private String storageName;
    private String imgPath;

    private String name;

    public ObservableField<Boolean> allFieldsSetObsv = new ObservableField<>();
    public ObservableField<Integer> componentCountObsv = new ObservableField<>();
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();
    public ObservableField<Boolean> isEditObsv = new ObservableField<>(false);


    public UIRack(long id, String name, String imgPath)
    {
        this.id = id;
        this.name = name;
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

    private void setAllFieldsSet()
    {
        if (name != null)
        {
            allFieldsSetObsv.set(name.length() > 1);
        }
        else { allFieldsSetObsv.set(false); }
    }

    @Override
    public String getForeignKeyName() { return storageName; }

    public void setStorageName(String storageName) { this.storageName = storageName; }

    public void setAmountOfComponents(int amount) { componentCountObsv.set(amount); }

    @Override
    public String getSecondaryForeignKeyName() { return null; }

    @Override
    public String getClassName() { return "Rack"; }

    @Override
    public Class getBelongingOverViewActivity() { return StorageOverViewActivity.class; }

    public String getAmountOfComponents() { return "Components: " + componentCountObsv.get(); }

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

    @Override
    public String getCount() { return " "; }

    @Override
    public boolean isComponent() { return false; }
}