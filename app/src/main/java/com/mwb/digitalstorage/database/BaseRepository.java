package com.mwb.digitalstorage.database;

import android.app.Application;

public class BaseRepository
{
    protected DAO dao;

    public BaseRepository(Application application)
    {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.dao();
    }

}