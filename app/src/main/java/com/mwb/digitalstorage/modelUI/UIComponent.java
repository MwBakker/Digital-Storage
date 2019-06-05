package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.RackOverViewActivity;
import androidx.databinding.ObservableField;


public class UIComponent implements UIEntity
{
    private long id;

    private String storageName;
    private String rackName;
    private String imgPath;

    public ObservableField<String> nameObsv = new ObservableField<>();
    public ObservableField<String> codeObsv = new ObservableField<>();
    public ObservableField<String> categoryNameObsv = new ObservableField<>();
    public ObservableField<String> countObsv = new ObservableField<>();
    public ObservableField<Bitmap> imgObsv = new ObservableField<>();
    public ObservableField<Boolean> isEditObsv = new ObservableField<>();
    // spinner adapter
    public ObservableField<ArrayAdapter<UIComponentCategory>> uiComponentCategorySpinnerAdapterObsv = new ObservableField<>();
    public ObservableField<Integer> selectedCategoryInListObsv = new ObservableField<>();


    public UIComponent(long id, String name, String code, String imgPath)
    {
        this.id = id;
        this.nameObsv.set(name);
        this.codeObsv.set(code);
        this.imgPath = imgPath;
    }


    @Override
    public long getId() { return id; }

    @Override
    public String getName() { return nameObsv.get(); }

    public void setStorageName(String storageName) { this.storageName = storageName; }

    @Override
    public String getForeignKeyName() { return rackName; }

    @Override
    public String getSecondaryForeignKeyName() { return storageName; }

    public void setRackName(String rackName) { this.rackName = rackName; }

    public void setCategoryName(String categoryName) { categoryNameObsv.set(categoryName); }

    @Override
    public String getClassName() { return "Component"; }

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

    void setCount(int count) { countObsv.set(Integer.toString(count)); }

    @Override
    public String getCount() { return countObsv.get(); }

    @Override
    public boolean isComponent() { return true; }
}