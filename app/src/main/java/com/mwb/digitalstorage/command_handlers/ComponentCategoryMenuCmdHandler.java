package com.mwb.digitalstorage.command_handlers;


import com.mwb.digitalstorage.command_handlers.entity.EntityMenuCmdHandler;

public interface ComponentCategoryMenuCmdHandler extends EntityMenuCmdHandler
{
    void addNewCategory();

    void removeNewCategory();
}