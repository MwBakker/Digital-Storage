package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import com.mwb.digitalstorage.modelUI.UIStorage;


public class StorageMenuViewModel extends BaseViewModel
{
    private StorageRepository storageRepository;
    private UIStorage uiStorage;


    public void setViewModelElements()
    {
        storageRepository = new StorageRepository();
        uiStorage = new UIStorage(0L, "", "", "");
    }

    //  gets the storage
    public UIStorage getUiStorage() { return uiStorage; }

    //  adds the storage, retrieves the through
    //  above callBack method
    public void addStorage(RackRepository rackRepository)
    {
        executor.execute(() ->
        {
           long storageID = storageRepository.insertStorage(uiStorage.getName(), uiStorage.getLocation(), uiStorage.getImgPath());
           addRacksToStorage(storageID, rackRepository);
        });
    }

    //  adds the new racks belonging to the newly created storage
    private void addRacksToStorage(long storageID, RackRepository rackRepository)
    {
        for (int i = 1; i <= uiStorage.rackAmountObsv.get(); i++)
        {
            String title = "Kast_" + i;
            executor.execute(() ->
            {
                rackRepository.insertRack(storageID, title, "");
            });
        }
    }
}