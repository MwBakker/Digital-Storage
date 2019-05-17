package com.mwb.digitalstorage.modelUI;

import android.graphics.Color;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


public class UIComponentCategory extends BaseObservable
{
    private long componentCatID;

    public ObservableField<Integer> selectedItemPosition = new ObservableField<>();
    public ObservableField<String> componentCatName = new ObservableField<>("");
    public ObservableField<String> amountOfComponents = new ObservableField<>("");
    public ObservableField<Boolean> isEdit = new ObservableField<>();
    public ObservableField<Boolean> isSelected = new ObservableField<>();
    public ObservableField<Integer> color = new ObservableField<>();


    public UIComponentCategory(long componentCatID, String componentCatName, int amountOfComponents)
    {
        this.componentCatID = componentCatID;
        this.componentCatName.set(componentCatName);
        this.amountOfComponents.set("(" + amountOfComponents + ")");
        isEdit.set(false);
        isSelected.set(false);
        color.set(Color.parseColor("#535458"));
    }

    public long getComponentCatID() { return this.componentCatID; }


    //  sets the component category, since this is not an official VM but a model extension
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        componentCatName.set(s.toString());
    }


    //  defines color and state of selection
    public boolean setSelectedState()
    {
        int parsedColor = Color.parseColor("#CFD8DC");
        this.color.set(parsedColor == this.color.get() ? Color.parseColor("#535458" ) : parsedColor);
        return parsedColor != this.color.get();
    }


    //  overrides toString for the right text display
    @Override
    public String toString()
    {
        return this.componentCatName.get();            // What to display in the Spinner list.
    }

    @Bindable
    public int getSelectedItemPosition()
    {
        return selectedItemPosition.get();
    }

    public void setSelectedItemPosition(int selectedItemPosition)
    {
        this.selectedItemPosition.set(selectedItemPosition);
    }
}