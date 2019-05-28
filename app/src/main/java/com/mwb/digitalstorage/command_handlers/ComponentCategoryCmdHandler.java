package com.mwb.digitalstorage.command_handlers;

import com.mwb.digitalstorage.modelUI.UIComponentCategory;

public interface ComponentCategoryCmdHandler
{
    void sort(UIComponentCategory uiComponentCategory);

    boolean editComponentCategory(UIComponentCategory uiComponentCategory);

    void saveEdit();
}