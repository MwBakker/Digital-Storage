package com.mwb.digitalstorage.command_handlers;


public interface SearchedEntityCmdHandler
{
    void goToSearchedEntity(Class classType, String className, long entityID);
}