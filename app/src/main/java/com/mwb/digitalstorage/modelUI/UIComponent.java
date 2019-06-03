package com.mwb.digitalstorage.modelUI;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.RackOverViewActivity;
import androidx.databinding.ObservableField;


public class UIComponent implements UIEntity
{
    private long id;
    private long rackID;
    private long componentCategoryID;

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


    public UIComponent(long id, long rackID, long componentCategoryID,
                       String name, String code, String imgPath, int count)
    {
        this.id = id;
        this.rackID = rackID;
        this.componentCategoryID = componentCategoryID;
        this.nameObsv.set(name);
        this.codeObsv.set(code);
        this.imgPath = imgPath;
        this.countObsv.set(Integer.toString(count));
    }


    @Override
    public long getId() { return id; }

    @Override
    public String getName() { return nameObsv.get(); }

    public void setStorageName() { this.storageName = storageName; }

    @Override
    public String getForeignKeyName() { return rackName; }

    @Override
    public String getSecondaryForeignKeyName() { return storageName; }

    public void setRackName() { this.rackName = rackName; }

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

    @Override
    public int getCount() { return Integer.parseInt(countObsv.get()); }

    @Override
    public boolean isComponent() { return true; }
}