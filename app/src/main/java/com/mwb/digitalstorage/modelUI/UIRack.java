package com.mwb.digitalstorage.modelUI;

import com.mwb.digitalstorage.StorageOverViewActivity;


public class UIRack extends BaseEntity implements UIEntity
{
    public long id;


    private String name;
    private String storageName;
    private int componentCount;


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

    @Override
    public String getSecondaryForeignKeyName() { return null; }

    @Override
    public Class getBelongingOverViewActivity() { return StorageOverViewActivity.class; }

    public int getAmountOfComponents() { return componentCount; }

    public void setAmountOfComponents(int amount) { componentCount = amount; }

    @Override
    public int getAmount() { return 0; }
}