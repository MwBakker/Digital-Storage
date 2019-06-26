package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.adapter.RackListAdapter;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.List;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;


public class RackOverViewViewModel extends BaseViewModel
{
    private UIStorage uiStorage;
    private UIRack uiRack;

    public ObservableField<RackListAdapter> rackListAdapterObsv = new ObservableField<>();


    public void setViewModelElements(long storageID)
    {
        uiStorage = repositoryFactory.storageRepository.getUIStorageUnit(storageID);
           //storageRepository.setUIStorageElements(uiStorage, imgProcessor);
    }

    public LiveData<List<UIRack>> getUIRacks(long storageID)
    {
        return repositoryFactory.rackRepository.getRacks(storageID);
    }

    // gets the uiStorage
    public UIStorage getUiStorage() { return uiStorage; }

    // gets the uiRack
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

    //
    //  deletes the rack
    //
    public void deleteRack()
    {
        repositoryFactory.rackRepository.deleteRack(uiRack.id);
    }
}