package com.mwb.digitalstorage.command_handlers;

import com.mwb.digitalstorage.command_handlers.entity.ImgCmdHandler;

public interface RegistrationCmdHandler extends ImgCmdHandler
{
    void editCompany();

    void editCompanyName(CharSequence s, int start, int before, int count);

    void editCompanyLocation(CharSequence s, int start, int before, int count);

    void saveCompany();
}
