package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.adapter.RackListAdapter;
import com.mwb.digitalstorage.command_handlers.RackCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.ImgCmdHandler;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.modelUI.UIStorage;
import androidx.databinding.ObservableField;


public class RackOverViewViewModel extends BaseViewModel
{
    private RackRepository rackRepository;
    private UIStorage uiStorage;
    private UIRack uiRack;

    public ObservableField<RackListAdapter> rackListAdapterObsv = new ObservableField<>();


    public void setViewModelElements(long storageID, androidx.lifecycle.LifecycleOwner owner,
                                      RackCmdHandler rackCmdHandler, ImgCmdHandler imgCmdHandler)
    {
        rackRepository = new RackRepository();
        rackRepository.getRacks(storageID).observe(owner, racks ->
        {
            rackListAdapterObsv.set(new RackListAdapter(racks, rackCmdHandler, imgCmdHandler, imgProcessor));
        });
        StorageRepository storageRepository = new StorageRepository();
        executor.execute(() ->
        {
            uiStorage = storageRepository.getStorageUnit(storageID);
            uiStorage.imgObsv.set(imgProcessor.decodeImgPath(uiStorage.getImgPath()));
        });
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
        executor.execute(() ->
        {
            rackRepository.editRack(uiRack.id, uiRack.nameObsv.get(), uiRack.getImgPath());
        });
        uiRack.isEditObsv.set(false);
    }

    //
    //  deletes the rack
    //
    public void deleteRack()
    {
        executor.execute(() -> { rackRepository.deleteRack(uiRack.id); });
    }
}