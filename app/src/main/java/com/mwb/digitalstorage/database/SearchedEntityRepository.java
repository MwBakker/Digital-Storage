package com.mwb.digitalstorage.database;

import android.app.Application;
import android.database.Observable;

import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.model.Entity;
import com.mwb.digitalstorage.model.Rack;
import com.mwb.digitalstorage.model.Storage;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.modelUI.UIStorage;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class SearchedEntityRepository
{
    private DAO dao;
    private List<UIEntity> uiEntities = new ArrayList<>();


    public SearchedEntityRepository(Application application)
    {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.dao();
    }

    //
    //  retrieve all found entities
    //

    public List<UIEntity> findEntities(String s)
    {
        if (uiEntities.size() > 1) { uiEntities.clear(); }

        for (Storage storage : dao.searchStorages("%" + s + "%"))
        {
            uiEntities.add(new UIStorage(storage.id, storage.getName(), storage.getLocation(), storage.getImgPath()));
        }
        for (Rack rack : dao.searchRacks("%" + s + "%"))
        {
            uiEntities.add(new UIRack(rack.id, rack.getName(), rack.getRackImgPath(), rack.getComponentCount()));
        }
        for (Component component : dao.searchComponents("%" + s + "%"))
        {
            uiEntities.add(new UIComponent(component.componentID, component.rackID, component.componentCategoryID, component.getCategoryName(),component.getName(),
                                           component.getCode(), component.getImgPath(), component.getCount()));
        }
        return uiEntities;
    }
}