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
        executor.execute(() ->
        {
            for (Storage storage : storageUnits)
            {
                UIStorageList.add(createUIStorage(storage));
            }
        });
        return UIStorageList;
    }

    //  returns one storage unit
    public UIStorage getUIStorageUnit(long id)
    {
        return createUIStorage(getDao().getStorageUnit(id));
    }

    //  returns uiStorage from storage
    private UIStorage createUIStorage(Storage storage)
    {
        UIStorage uiStorage = new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath());
        uiStorage.setAmountOfRacks(getDao().getAmountOfRacks(storage.id));
        uiStorage.setAmountOfComponents(getDao().getAmountOfComponents(storage.id));
        return uiStorage;
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