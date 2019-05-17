package com.mwb.digitalstorage.command_handlers;

public interface SearchCmdHandler
{
    void onTextChanged(CharSequence s, int start, int before, int count);

    void goBack();
}
