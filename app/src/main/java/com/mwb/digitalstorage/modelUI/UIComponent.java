package com.mwb.digitalstorage.modelUI;

import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.RackOverViewActivity;
import androidx.databinding.ObservableField;


public class UIComponent extends BaseEntity implements UIEntity
{
    private final long id;
    public final long rackID;
    public final long categoryID;

    private String storageName;
    private String rackName;
    private String name;
    private String code;
    private int amount;

    public ObservableField<String> categoryNameObsv = new ObservableField<>();
    // spinner adapter
    public ObservableField<ArrayAdapter<UIComponentCategory>> uiComponentCategorySpinnerAdapterObsv = new ObservableField<>();
    public ObservableField<Integer> selectedCategoryInListObsv = new ObservableField<>();


    public UIComponent(long id, long rackID, long categoryID, String name, String code, String imgPath)
    {
        this.id = id;
        this.categoryID = categoryID;
        this.rackID = rackID;
        this.name = name;
        this.code = code;
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

    public String getCode() { return code; }

    public void setCode(String code)
    {
        this.code = code;
        setAllFieldsSet();
    }

    private void setAllFieldsSet()
    {
        if (name != null && code != null && (categoryNameObsv.get() != null || selectedCategoryInListObsv != null))
        {
            allFieldsSetObsv.set(name.length() > 1 && code.length() > 1);
        }
        else { allFieldsSetObsv.set(false); }
    }

    public void setStorageName(String storageName) { this.storageName = storageName; }

    @Override
    public String getForeignKeyName() { return rackName; }

    @Override
    public String getSecondaryForeignKeyName() { return storageName; }

    public void setRackName(String rackName) { this.rackName = rackName; }

    public void setCategoryName(String categoryName) { categoryNameObsv.set(categoryName); }

    @Override
    public Class getBelongingOverViewActivity() { return RackOverViewActivity.class; }

    public void setAmount(int amount) { this.amount = amount; }

    @Override
    public int getAmount() { return amount; }
}