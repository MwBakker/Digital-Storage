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
    public LiveData<List<UIRack>> getRacks(long storageID)
    {
        // transform one list to another
        return Transformations.map(getDao().getRacks(storageID), newData -> createRackUIList(newData));
    }

    //  manually sets the rack to rackUI
    private List<UIRack> createRackUIList(List<Rack> rackList)
    {
        List<UIRack> UIRackList = new ArrayList<>();
        for (Rack rack : rackList)
        {
            UIRackList.add(new UIRack(rack.id, rack.getName(), rack.getRackImgPath()));
        }
        return UIRackList;
    }

    //  returns rack
    public UIRack getUIRack(long rackID)
    {
        Rack rack = getDao().getRack(rackID);
        return  new UIRack(rack.id, rack.getName(), rack.getRackImgPath());
    }

    //  sets the elements of the uiStorage
    public void setUIRackElements(UIRack uiRack)
    {
        uiRack.setAmountOfComponents(getDao().getAmountOfComponents(uiRack.id));
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