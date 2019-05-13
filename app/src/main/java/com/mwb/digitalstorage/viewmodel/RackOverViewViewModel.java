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

    public ObservableField<Bitmap> storageImgObs = new ObservableField<>();
    public ObservableField<RackListAdapter> rackListAdapterObs = new ObservableField<>();


    public RackOverViewViewModel(long storageID, StorageRepository storageRepository, RackRepository rackRepository, androidx.lifecycle.LifecycleOwner owner,
                                 RackCmdHandler rackCmdHandler, PhotoCmdHandler photoCmdHandler)
    {
        this.rackRepository = rackRepository;
        this.storageID = storageID;
        rackRepository.getRacks(storageID).observe(owner, racks ->
        {
            rackListAdapterObs.set(new RackListAdapter(racks, rackCmdHandler, photoCmdHandler));
        });
        executor.execute(() ->
        {
            UIStorage UIStorage = storageRepository.getStorageUnit(storageID);
            storageName = UIStorage.name.get();
            storageLoc = UIStorage.location.get();
            storageImgObs.set(UIStorage.getImg());
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
           this.uiRack.isEdit.set(false);
        }
        uiRack.isEdit.set(true);
        this.uiRack = uiRack;
    }

    //
    //  saves the edit made on the rack
    //
    public void saveRackEdit()
    {
        executor.execute(() ->
        {
            rackRepository.editRack(uiRack.id, uiRack.rackName.get(), uiRack.getImgPath());
        });
        uiRack.isEdit.set(false);
    }

    //
    //  deletes the rack
    //
    public void deleteRack()
    {
        executor.execute(() -> { rackRepository.deleteRack(uiRack.id); });
    }
}