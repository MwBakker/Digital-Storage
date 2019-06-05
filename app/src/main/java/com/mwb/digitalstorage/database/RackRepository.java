package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Rack;
import com.mwb.digitalstorage.modelUI.UIRack;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class RackRepository extends BaseRepository
{
    //  returns all racks with correct storage_id
    public LiveData<List<UIRack>> getRacks(long storageID, Executor executor)
    {
        // transform one list to another
        return Transformations.map(getDao().getRacks(storageID), newData -> createRackUIList(newData, executor));
    }

    //  manually sets the rack to rackUI
    private List<UIRack> createRackUIList(List<Rack> rackList, Executor executor)
    {
        List<UIRack> UIRackList = new ArrayList<>();
        executor.execute(() ->
        {
            for (Rack rack : rackList)
            {
                UIRackList.add(createUIRack(rack));
            }
        });
        return UIRackList;
    }

    //  returns rack
    public UIRack getUIRack(long rackID)
    {
        return createUIRack(getDao().getRack(rackID));
    }

    //  return uiRack from rack
    private UIRack createUIRack(Rack rack)
    {
        UIRack uiRack = new UIRack(rack.id, rack.getName(), rack.getRackImgPath());
        uiRack.setAmountOfComponents(getDao().getAmountOfComponents(rack.id));
        return uiRack;
    }

    //  performs rack insert
    public void insertRack(long storageID, String rackName, String rackImgPath)
    {
        getDao().insertRack(new Rack(storageID, rackName, rackImgPath));
    }

    //  edits the rack
    public void editRack(long rackID, String rackName, String rackImgPath)
    {
        getDao().editRack(rackID, rackName, rackImgPath);
    }

    //  deletes the rack
    public void deleteRack(long rackID)
    {
        getDao().deleteRack(rackID);
    }
}