package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.modelUI.UIRack;


public class RackMenuViewModel extends BaseViewModel
{
    private long storageID;
    private RackRepository rackRepository;

    private UIRack uiRack;


    public void setViewModelElements(long storageID)
    {
        this.storageID = storageID;
        rackRepository = new RackRepository();
        uiRack = new UIRack(0L, "", "");
    }

    //  gets the uiRack
    public UIRack getUiRack() { return uiRack; }

    //  gets the involved storage
    public long getStorageID() { return storageID; }

    //  add rack to repository
    public void addRack()
    {
        executor.execute(() -> { rackRepository.insertRack(storageID, uiRack.nameObsv.get(), uiRack.getImgPath()); });
    }
}