package com.mwb.digitalstorage.database;

import android.app.Application;

import com.mwb.digitalstorage.database.DAO.DAO;

public abstract class BaseRepository
{
    private static DAO dao;

    public static void setDao(Application application)
    {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.dao();
    }

    static DAO getDao() { return dao; }
}