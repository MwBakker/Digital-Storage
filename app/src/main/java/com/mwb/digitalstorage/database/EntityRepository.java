package com.mwb.digitalstorage.database;

import android.app.Application;

import com.mwb.digitalstorage.model.Entity;
import com.mwb.digitalstorage.modelUI.UIEntity;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.LiveData;


public class EntityRepository
{
    private DAO dao;


    public EntityRepository(Application application)
    {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.dao();
    }

    //
    //  returns allRacks with correct storage_id
    //
    public LiveData<List<UIEntity>> getFoundEntities()
    {
        // transform one list to another
        //return Transformations.map(dao.getStorageRacks(storageID), newData -> createRackUI(newData));
        return null;
    }

    //
    //  manually sets the rack to rackUI
    //
    private List<UIEntity> createRackUI(List<Entity> entityList)
    {
        List<UIEntity> UIEntityList = new ArrayList<>();
        for (Entity entity : entityList)
        {
            //UIEntityList.add(new UIRack(entity.rackID, entity.storageID, entity.getRackName(),
             //       entity.getRackImgPath()));
        }
        return UIEntityList;
    }
}
