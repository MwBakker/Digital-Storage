package com.mwb.digitalstorage.viewmodel;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.adapter.RackListAdapter;
import com.mwb.digitalstorage.command_handlers.RackCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.PhotoCmdHandler;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.modelUI.UIStorage;
import androidx.databinding.ObservableField;


public class RackOverViewViewModel extends BaseViewModel
{
    private long storageID;
    private RackRepository rackRepository;
    private String storageName;
    private String storageLoc;
    private UIRack uiRack;

    public ObservableField<Bitmap> storageImgObsv = new ObservableField<>();
    public ObservableField<RackListAdapter> rackListAdapterObsv = new ObservableField<>();


    public void setViewModelElements(long storageID, androidx.lifecycle.LifecycleOwner owner,
                                      RackCmdHandler rackCmdHandler, PhotoCmdHandler photoCmdHandler)
    {
        rackRepository = new RackRepository();
        StorageRepository storageRepository = new StorageRepository();
        this.storageID = storageID;
        rackRepository.getRacks(storageID).observe(owner, racks ->
        {
            rackListAdapterObsv.set(new RackListAdapter(racks, rackCmdHandler, photoCmdHandler));
        });
        executor.execute(() ->
        {
            UIStorage UIStorage = storageRepository.getStorageUnit(storageID);
            storageName = UIStorage.nameObsv.get();
            storageLoc = UIStorage.locationObsv.get();
            storageImgObsv.set(UIStorage.getImg());
        });
    }

    public UIRack getUiRack() { return uiRack; }

    public long getStorageID() { return storageID; }

    public String getStorageName() { return storageName; }

    public String getStorageLoc() { return storageLoc; }

    //
    //  sets the editable rack
    //  first set the previous rack to non-edit status
    //
    public void setEditableRack(UIRack uiRack)
    {
        if (this.uiRack != null)
        {
           this.uiRack.isEditObsv.set(false);
        }
        uiRack.isEditObsv.set(true);
        this.uiRack = uiRack;
    }

    //
    //  saves the edit made on the rack
    //
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