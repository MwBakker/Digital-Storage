package com.mwb.digitalstorage.command_handlers;

import com.mwb.digitalstorage.command_handlers.entity.PhotoCmdHandler;

public interface RegistrationCmdHandler extends PhotoCmdHandler
{
    void editCompany();

    void editCompanyName(CharSequence s, int start, int before, int count);

    void editCompanyLocation(CharSequence s, int start, int before, int count);

    void saveCompany();
}
