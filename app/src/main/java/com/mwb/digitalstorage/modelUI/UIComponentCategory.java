package com.mwb.digitalstorage.modelUI;

import android.graphics.Color;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


public class UIComponentCategory extends BaseObservable
{
    private long id;

    public ObservableField<Integer> selectedItemPosition = new ObservableField<>();
    public ObservableField<String> nameObsv = new ObservableField<>("");
    public ObservableField<String> componentAmountObsv = new ObservableField<>("");
    public ObservableField<Boolean> isEditObsv = new ObservableField<>();
    public ObservableField<Boolean> isSelectedObsv = new ObservableField<>();
    public ObservableField<Integer> colorObsv = new ObservableField<>();


    public UIComponentCategory(long id, String name, int amountOfComponents)
    {
        this.id = id;
        this.nameObsv.set(name);
        this.componentAmountObsv.set("(" + amountOfComponents + ")");
        isEditObsv.set(false);
        isSelectedObsv.set(false);
        colorObsv.set(Color.parseColor("#535458"));
    }

    public long getID() { return this.id; }


    //  defines color and state of selection
    public boolean setSelectedState()
    {
        int parsedColor = Color.parseColor("#CFD8DC");
        this.colorObsv.set(parsedColor == this.colorObsv.get() ? Color.parseColor("#535458" ) : parsedColor);
        return parsedColor != this.colorObsv.get();
    }


    //  overrides toString for the right text display
    @Override
    public String toString() { return this.nameObsv.get(); }

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