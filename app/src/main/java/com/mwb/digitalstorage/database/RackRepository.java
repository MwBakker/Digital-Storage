package com.mwb.digitalstorage.database;

import android.app.Application;
import com.mwb.digitalstorage.model.Rack;
import com.mwb.digitalstorage.model.Storage;
import com.mwb.digitalstorage.modelUI.UIRack;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class RackRepository
{
    private DAO dao;


    public RackRepository(Application application)
    {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.dao();
    }

    //
    //  returns allRacks with correct storage_id
    //
    public LiveData<List<UIRack>> getRacks(long storageID)
    {
        // transform one list to another
        return Transformations.map(dao.getStorageRacks(storageID), newData -> createRackUI(newData));
    }

    //
    //  manually sets the rack to rackUI
    //
    private List<UIRack> createRackUI(List<Rack> rackList)
    {
        List<UIRack> UIRackList = new ArrayList<>();
        for (Rack rack : rackList)
        {
            UIRackList.add(new UIRack(rack.id, rack.getName(), rack.getRackImgPath(),
                                      rack.getComponentCount()));
        }
        return UIRackList;
    }

    //
    //  returns rack
    //
    public UIRack getRack(long rackID)
    {
        Rack rack = dao.getRack(rackID);
        return new UIRack(rackID, rack.getName(), rack.getRackImgPath(), rack.getComponentCount());
    }

    //
    //  performs rack insert
    //
    public void insertRack(long storageID, String rackName, String rackImgPath)
    {
        dao.insertRack(new Rack(storageID, rackName, rackImgPath, 0));
    }

    //
    //  edits the rack
    //
    public void editRack(long rackID, String rackName, String rackImgPath)
    {
        dao.editRack(rackID, rackName, rackImgPath);
    }

    //
    //  deletes the storage
    //
    public void deleteRack(long rackID)
    {
        dao.deleteRack(rackID);
    }
}