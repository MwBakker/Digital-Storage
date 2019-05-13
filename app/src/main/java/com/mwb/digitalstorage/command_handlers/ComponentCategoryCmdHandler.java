package com.mwb.digitalstorage.command_handlers;

import com.mwb.digitalstorage.modelUI.UIComponentCategory;

public interface ComponentCategoryCmdHandler
{
    void sort(UIComponentCategory uiComponentCategory);

    boolean editComponentCat(UIComponentCategory uiComponentCategory);

    void editComponentName(CharSequence s, int start, int before, int count);

    void saveEdit();
}
