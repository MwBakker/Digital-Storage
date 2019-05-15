package com.mwb.digitalstorage.command_handlers.entity;

public interface EntityMenuCmdHandler extends PhotoCmdHandler
{
    void saveNewEntity();

    void cancelMenu();
}