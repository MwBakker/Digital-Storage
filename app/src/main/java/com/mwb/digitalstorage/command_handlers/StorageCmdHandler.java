package com.mwb.digitalstorage.command_handlers;

import com.mwb.digitalstorage.command_handlers.entity.EntityCmdHandler;


public interface StorageCmdHandler extends EntityCmdHandler
{
    void editStorageLocation(CharSequence s, int start, int before, int count);
}