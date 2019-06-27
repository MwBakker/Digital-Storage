package com.mwb.digitalstorage.modelUI;

import com.mwb.digitalstorage.RackOverViewActivity;


public class UIStorage extends BaseEntity implements UIEntity
{
    public final long id;
    private String name;
    private String location;
    private int amountOfRacks;
    private int amountOfComponents;


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
    public Class getBelongingOverViewActivity() { return RackOverViewActivity.class; }

    public void setAmountOfRacks(int amountOfRacks) { this.amountOfRacks = amountOfRacks; }

    public int getAmountOfRacks() { return amountOfRacks; }

    public void setAmountOfComponents(int amountOfComponents) { this.amountOfComponents = amountOfComponents; }

    public int getAmountOfComponents() { return amountOfComponents; }

    @Override
    public int getAmount() { return 0; }
}