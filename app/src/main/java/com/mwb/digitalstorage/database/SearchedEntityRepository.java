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


public class SearchedEntityRepository
{
    private List<UIEntity> uiEntities = new ArrayList<>();


    //  retrieve all found entities
    public List<UIEntity> findEntities(String s)
    {
        if (uiEntities.size() > 0) { uiEntities.clear(); }
        for (Storage storage : BaseRepository.getDao().searchStorageUnits("%" + s + "%"))
        {
            uiEntities.add(new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath()));
        }
        for (Rack rack : BaseRepository.getDao().searchRacks("%" + s + "%"))
        {
            UIRack uiRack = new UIRack(rack.id, rack.getName(), rack.getRackImgPath(), rack.getComponentCount());
            uiRack.setStorageName("(Storage) " + BaseRepository.getDao().getStorageUnit(rack.storageID).getName());
            uiEntities.add(uiRack);
        }
        for (Component component : BaseRepository.getDao().searchComponents("%" + s + "%"))
        {
            UIComponent uiComponent = new UIComponent(component.componentID, component.rackID, component.componentCategoryID,
                                                        component.getName(), component.getCode(), component.getImgPath(), component.getCount());
            Rack rack = BaseRepository.getDao().getRack(component.rackID);
            uiComponent.setRackName("(Rack) " + BaseRepository.getDao().getRack(component.rackID).getName());
            uiComponent.setStorageName("(Storage) " + BaseRepository.getDao().getStorageUnit(rack.storageID).getName());
            uiEntities.add(uiComponent);
        }
        return uiEntities;
    }
}