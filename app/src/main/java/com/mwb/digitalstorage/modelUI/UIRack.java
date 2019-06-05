package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.StorageOverViewActivity;
import androidx.databinding.ObservableField;


public class UIRack implements UIEntity
{
    public long id;
    private String storageName;
    private int amountOfComponents;
    private String imgPath;

    public ObservableField<String> nameObsv = new ObservableField<>("");
    public ObservableField<Integer> componentCountObsv = new ObservableField<>();
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();
    public ObservableField<Boolean> isEditObsv = new ObservableField<>(false);


    public UIRack(long id, String name, String imgPath)
    {
        this.id = id;
        this.nameObsv.set(name);
        this.imgPath = imgPath;
    }

    @Override
    public long getId() { return id; }

    @Override
    public String getName() { return nameObsv.get(); }

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

    public String getAmountOfComponents() { return "Components: " + amountOfComponents; }


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

    @Override
    public String getCount() { return " "; }

    @Override
    public boolean isComponent() { return false; }
}