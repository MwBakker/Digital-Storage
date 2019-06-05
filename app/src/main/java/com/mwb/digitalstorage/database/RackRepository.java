package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Rack;
import com.mwb.digitalstorage.modelUI.UIRack;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class RackRepository extends BaseRepository
{
    //  returns all racks with correct storage_id
    public LiveData<List<UIRack>> getRacks(long storageID)
    {
        // transform one list to another
        return Transformations.map(getDao().getRacks(storageID), newData -> createRackUI(newData));
    }

    //  manually sets the rack to rackUI
    private List<UIRack> createRackUI(List<Rack> rackList)
    {
        List<UIRack> UIRackList = new ArrayList<>();
        for (Rack rack : rackList)
        {
            UIRack uiRack = new UIRack(rack.id, rack.getName(), rack.getRackImgPath());
            uiRack.setAmountOfComponents(getDao().getAmountOfComponents(rack.id));
            UIRackList.add(uiRack);
        }
        return UIRackList;
    }

    //  returns rack
    public UIRack getUIRack(long rackID)
    {
        Rack rack = getDao().getRack(rackID);
        UIRack uiRack = new UIRack(rackID, rack.getName(), rack.getRackImgPath());
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