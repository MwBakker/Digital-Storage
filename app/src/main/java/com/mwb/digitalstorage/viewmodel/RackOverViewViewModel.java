package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.command_handlers.entity.RetrieveEntityCmdHandler;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.List;
import androidx.lifecycle.LiveData;


public class RackOverViewViewModel extends BaseViewModel
{
    private UIStorage uiStorage;
    private UIRack uiRack;


    //  gets the involved company
    public void getUiStorageFromDB(long id, RetrieveEntityCmdHandler retrieveEntityCmdHandler)
    {
        repositoryFactory.storageRepository.getUIStorage(id, retrieveEntityCmdHandler);
    }

    //  gets the uiStorage
    public UIStorage getUiStorage() { return uiStorage; }

    //  sets the involved company
    public void setUiStorage(UIStorage uiStorage) { this.uiStorage = uiStorage; }

    //  gets all the uiRacks
    public LiveData<List<UIRack>> getUIRacks(long storageID)
    {
        return repositoryFactory.rackRepository.getRacks(storageID);
    }

    //  sets the elements belonging to the rack
    public void setUiRackCountingProperties(List<UIRack> uiRacks)
    {
        repositoryFactory.rackRepository.setUiRackProperties(uiRacks, imgProcessor);
    }

    //  gets the uiRack
    public UIRack getUiRack() { return uiRack; }

    //  sets the editable rack
    //  first set the previous rack to non-edit status
    public void setEditableRack(UIRack uiRack)
    {
        if (this.uiRack != null)
        {
           this.uiRack.isEditObsv.set(false);
        }
        uiRack.isEditObsv.set(true);
        this.uiRack = uiRack;
    }

    //  saves the edit made on the rack
    public void saveRackEdit()
    {
        repositoryFactory.rackRepository.editRack(uiRack.id, uiRack.getName(), uiRack.getImgPath());
        uiRack.isEditObsv.set(false);
    }

    //  deletes the rack
    public void deleteRack()
    {
        repositoryFactory.rackRepository.deleteRack(uiRack.id);
    }
}