package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.misc.ImageProcessor;
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
    public void setUIRackElements(UIRack uiRack, ImageProcessor imgProcessor)
    {
        uiRack.imgObsv.set(imgProcessor.decodeImgPath(uiRack.getImgPath()));
        uiRack.setAmountOfComponents(getDao().getAmountOfComponents(uiRack.id));
    }

    //  performs rack insert
    public void insertRack(long storageID, String name, String imgPath)
    {
        executor.execute(() -> { getDao().insertRack(new Rack(storageID, name, imgPath)); });
    }

    //  edits the rack
    public void editRack(long id, String name, String imgPath)
    {
        executor.execute(() -> { getDao().editRack(id, name, imgPath); });
    }

    //  deletes the rack
    public void deleteRack(long id)
    {
        executor.execute(() -> { getDao().deleteRack(id); });
    }
}