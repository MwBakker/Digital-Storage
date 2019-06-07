package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Storage;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class StorageRepository extends BaseRepository
{
    //  returns allRacks with correct storage_id
    public LiveData<List<UIStorage>> getStorageUnits(Executor executor)
    {
        LiveData<List<Storage>> storageUnits = getDao().getStorageUnits();
        // transform one list to another
        return Transformations.map(storageUnits, newData -> createStorageUIList(newData, executor));
    }

    //  manually sets the storage to storageUI
    private List<UIStorage> createStorageUIList(List<Storage> storageUnits, Executor executor)
    {
        List<UIStorage> UIStorageList = new ArrayList<>();
        for (Storage storage : storageUnits)
        {
            UIStorageList.add(new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath()));
        }
        return UIStorageList;
    }

    //  sets the elements of the uiStorage
    public void setUIStorageElements(UIStorage uiStorage)
    {
        uiStorage.setAmountOfRacks(getDao().getAmountOfRacks(uiStorage.id));
        uiStorage.setAmountOfComponents(getDao().getAmountOfComponents(uiStorage.id));
    }

    //  returns one storage unit
    public UIStorage getUIStorageUnit(long id)
    {
        Storage storage = getDao().getStorageUnit(id);
        return new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath());
    }

    //  performs storage insert
    public long insertStorage(String storageName, String storageLoc, String imgPath)
    {
        return getDao().insertStorage(new Storage(storageName, storageLoc, imgPath));
    }

    //  edits the storage
    public void editStorage(long storageID, String storageName, String storageLoc)
    {
        getDao().editStorage(storageID, storageName, storageLoc);
    }

    //  deletes the storage
    public void deleteStorage(long storageID)
    {
        getDao().deleteStorage(storageID);
    }
}