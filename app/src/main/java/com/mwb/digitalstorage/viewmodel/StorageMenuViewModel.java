package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.command_handlers.entity.EntityInsertCmdHandler;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.modelUI.UIStorage;


public class StorageMenuViewModel extends BaseViewModel
{
    private UIStorage uiStorage;


    public void setViewModelElements()
    {
        uiStorage = new UIStorage(0L, "", "", "");
    }

    //  gets the storage
    public UIStorage getUiStorage() { return uiStorage; }

    //  adds the storage, retrieves the through
    //  above callBack method
    public void addStorage(EntityInsertCmdHandler entityInsertCmdHandler)
    {
        repositoryFactory.storageRepository.insertStorage(uiStorage.getName(), uiStorage.getLocation(), uiStorage.getImgPath(),
                                                                entityInsertCmdHandler);
    }

    //  adds the new racks belonging to the newly created storage
    public void addRacksToStorage(long storageID, RackRepository rackRepository)
    {
        for (int i = 1; i <= uiStorage.getAmountOfRacks(); i++)
        {
            String title = "Kast_" + i;
            rackRepository.insertRack(storageID, title, "");
        }
    }
}