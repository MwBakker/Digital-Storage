package com.mwb.digitalstorage.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.mwb.digitalstorage.model.Storage;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.ArrayList;
import java.util.List;


public class StorageRepository extends BaseRepository
{

    public StorageRepository(Application application)
    {
        super(application);
    }

    //
    //  returns allRacks with correct storage_id
    //
    public LiveData<List<UIStorage>> getStorageUnits()
    {
        LiveData<List<Storage>> storageUnits = dao.getAllStorageUnits();
        // transform one list to another
        return Transformations.map(storageUnits, newData -> createStorageUI(newData));
    }

    //
    //  manually sets the storage to storageUI
    //
    private List<UIStorage> createStorageUI(List<Storage> storageUnits)
    {
        List<UIStorage> UIStorageList = new ArrayList<>();
        for (Storage storage : storageUnits)
        {
            UIStorageList.add(new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath()));
        }
        return UIStorageList;
    }

    //
    //  returns one storage unit
    //
    public UIStorage getStorageUnit(long storageID)
    {
        Storage storage = dao.getStorageUnit(storageID);
        return new UIStorage(storageID, storage.getName(), storage.getLocation(), storage.getImgPath());
    }

    //
    //  performs storage insert
    //
    public long insertStorage(String storageName, String storageLoc, String imgPath)
    {
        return dao.insertStorage(new Storage(storageName, storageLoc, imgPath));
    }

    //
    //  edits the storage
    //
    public void editStorage(long storageID, String storageName, String storageLoc)
    {
        dao.editStorage(storageID, storageName, storageLoc);
    }

    //
    //  deletes the storage
    //
    public void deleteStorage(long storageID)
    {
        dao.deleteStorage(storageID);
    }
}