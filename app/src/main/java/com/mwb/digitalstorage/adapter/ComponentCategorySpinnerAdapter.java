package com.mwb.digitalstorage.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import androidx.annotation.NonNull;


public class ComponentCategorySpinnerAdapter extends ArrayAdapter<UIComponentCategory>
{

    public ComponentCategorySpinnerAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
    }


}
