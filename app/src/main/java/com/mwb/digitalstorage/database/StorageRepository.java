package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.command_handlers.entity.EntityInsertCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.RetrieveEntityCmdHandler;
import com.mwb.digitalstorage.model.Storage;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class StorageRepository extends BaseRepository
{
    //  gets all storage units
    public LiveData<List<UIStorage>> getStorageUnits()
    {
        return Transformations.map(getDao().getStorageUnits(), newData -> createStorageUIList(newData));
    }

    //  manually sets the storage to storageUI
    private List<UIStorage> createStorageUIList(List<Storage> storageUnits)
    {
        List<UIStorage> UIStorageList = new ArrayList<>();
        for (Storage storage : storageUnits)
        {
            UIStorage uiStorage = new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath());
            UIStorageList.add(uiStorage);
        }
        return UIStorageList;
    }

    //  sets the elements of the uiStorage
    //  no callback required due to use of Observables in the uiStorage's fields
    public void setStorageUnitCountings(List<UIStorage> storageUnits)
    {
        executor.execute(() ->
        {
            for (UIStorage uiStorage : storageUnits)
            {
                uiStorage.setAmountOfRacks(getDao().getAmountOfRacks(uiStorage.id));
                uiStorage.setAmountOfComponents(getDao().getAmountOfComponents(uiStorage.id));
            }
        });
    }

    //  returns one storage unit
    public UIStorage getUIStorageUnit(long id)
    {
        Storage storage = getDao().getStorageUnit(id);
        return new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath());
    }

    //  performs storage insert
    public void insertStorage(String name, String location, String imgPath, EntityInsertCmdHandler entityInsertCmdHandler)
    {
        executor.execute(() -> {
            entityInsertCmdHandler.insertion(getDao().insertStorage(new Storage(name, location, imgPath)));
        });
    }

    //  edits the storage
    public void editStorage(long id, String name, String location)
    {
        executor.execute(() -> { getDao().editStorage(id, name, location); });
    }

    //  deletes the storage
    public void deleteStorage(long id)
    {
        executor.execute(() -> { getDao().deleteStorage(id); });
    }
}