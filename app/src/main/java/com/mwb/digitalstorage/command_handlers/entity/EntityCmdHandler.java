package com.mwb.digitalstorage.command_handlers.entity;

import com.mwb.digitalstorage.modelUI.UIEntity;


public interface EntityCmdHandler
{
    void addNewEntity();

    void enterEntity(long id);

    boolean editEntity(UIEntity uiEntity);

    void editEntityTitle(CharSequence s, int start, int before, int count);

    void saveEntity(boolean isNew);

    void deleteEntity();
}