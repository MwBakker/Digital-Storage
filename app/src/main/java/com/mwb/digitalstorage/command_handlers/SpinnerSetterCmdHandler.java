package com.mwb.digitalstorage.command_handlers;

import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import java.util.List;

public interface SpinnerSetterCmdHandler
{
    ArrayAdapter setComponentCategorySpinner(List<UIComponentCategory> uiComponentCategories);
}