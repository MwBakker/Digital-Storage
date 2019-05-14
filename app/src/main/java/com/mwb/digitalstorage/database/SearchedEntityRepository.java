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


    //
    //  retrieve all found entities
    //
    public List<UIEntity> findEntities(String s)
    {
        if (uiEntities.size() > 1) { uiEntities.clear(); }

        for (Storage storage : BaseRepository.getDao().searchStorages("%" + s + "%"))
        {
            uiEntities.add(new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath()));
        }
        for (Rack rack : BaseRepository.getDao().searchRacks("%" + s + "%"))
        {
            uiEntities.add(new UIRack(rack.id, rack.getName(), rack.getRackImgPath(), rack.getComponentCount()));
        }
        for (Component component : BaseRepository.getDao().searchComponents("%" + s + "%"))
        {
            uiEntities.add(new UIComponent(component.componentID, component.rackID, component.componentCategoryID, component.getCategoryName(),component.getName(),
                                           component.getCode(), component.getImgPath(), component.getCount()));
        }
        return uiEntities;
    }
}