package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.model.Rack;
import com.mwb.digitalstorage.model.Storage;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.ArrayList;
import java.util.List;


public class SearchedEntityRepository extends BaseRepository
{
    private List<UIEntity> uiEntities = new ArrayList<>();


    //  retrieve all found entities
    public List<UIEntity> findEntities(String s)
    {
        if (uiEntities.size() > 0) { uiEntities.clear(); }
        for (Storage storage : getDao().getStorageUnits("%" + s + "%"))
        {
            uiEntities.add(new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath()));
        }
        for (Rack rack : getDao().getRacks("%" + s + "%"))
        {
            UIRack uiRack = new UIRack(rack.id, rack.getName(), rack.getRackImgPath());
            uiRack.setStorageName("(Storage) " + getDao().getStorageUnit(rack.storageID).getName());
            uiEntities.add(uiRack);
        }
        for (Component component : getDao().getComponents("%" + s + "%"))
        {
            UIComponent uiComponent = new UIComponent(component.componentID, component.rackID, component.componentCategoryID,
                                                      component.getName(), component.getCode(), component.getImgPath());
            Rack rack = getDao().getRack(component.rackID);
            uiComponent.setRackName("(Rack) " + getDao().getRack(component.rackID).getName());
            uiComponent.setStorageName("(Storage) " + getDao().getStorageUnit(rack.storageID).getName());
            uiEntities.add(uiComponent);
        }
        return uiEntities;
    }
}