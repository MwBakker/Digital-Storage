package com.mwb.digitalstorage.database;

import android.app.Application;

public class BaseRepository
{
    private static DAO dao;

    public static void setDao(Application application)
    {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.dao();
    }

    public static DAO getDao() { return dao; }
}