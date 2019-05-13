package com.mwb.digitalstorage.command_handlers;

import com.mwb.digitalstorage.command_handlers.entity.EntityCmdHandler;
import com.mwb.digitalstorage.modelUI.UIStorage;


public interface StorageCmdHandler extends EntityCmdHandler
{
    void editStorageLocation(CharSequence s, int start, int before, int count);

    void editCompany();

    void editCompanyName(CharSequence s, int start, int before, int count);

    void saveCompanyEdit();
}