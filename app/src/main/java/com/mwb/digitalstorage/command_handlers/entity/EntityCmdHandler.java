package com.mwb.digitalstorage.command_handlers.entity;

import com.mwb.digitalstorage.modelUI.UIEntity;

public interface EntityCmdHandler
{
    void enterEntity(long id);

    void addNewEntity();

    boolean editEntity(UIEntity uiEntity);

    void editEntityTitle(CharSequence s, int start, int before, int count);

    void saveEntityEdit();

    void deleteEntity();
}