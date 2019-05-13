package com.mwb.digitalstorage.command_handlers;

import com.mwb.digitalstorage.command_handlers.entity.EntityMenuCmdHandler;

public interface StorageMenuCmdHandler extends EntityMenuCmdHandler
{
     void numberPickerValChanged(int newVal);
}