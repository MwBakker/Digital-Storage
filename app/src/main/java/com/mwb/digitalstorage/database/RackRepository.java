package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.command_handlers.entity.RetrieveEntityCmdHandler;
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
    public void getUIRack(long rackID, ImageProcessor imageProcessor, RetrieveEntityCmdHandler retrieveEntityCmdHandler)
    {
        executor.execute(() ->
        {
            Rack rack = getDao().getRack(rackID);
            UIRack uiRack = new UIRack(rack.id, rack.getName(), rack.getRackImgPath());
            setUIRackProperties(uiRack, imageProcessor);
            retrieveEntityCmdHandler.entityRetrieved(uiRack);
        });
    }

    //  sets the elements of the uiStorage, the observers get updated when finished
    public void setUiRackProperties(List<UIRack> uiRacks, ImageProcessor imgProcessor)
    {
        executor.execute(() ->
        {
            for (UIRack uiRack : uiRacks)
            {
                uiRack.imgObsv.set(imgProcessor.decodeImgPath(uiRack.getImgPath()));
                uiRack.componentAmountObsv.set(getDao().getAmountOfComponents(uiRack.id));
            }
        });
    }

    //  sets the elements of the uiRacks
    private void setUIRackProperties(UIRack uiRack, ImageProcessor imgProcessor)
    {
        uiRack.imgObsv.set(imgProcessor.decodeImgPath(uiRack.getImgPath()));
        uiRack.componentAmountObsv.set(getDao().getAmountOfComponents(uiRack.id));
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