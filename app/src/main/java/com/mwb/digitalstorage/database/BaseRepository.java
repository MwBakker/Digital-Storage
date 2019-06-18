package com.mwb.digitalstorage.database;

import android.app.Application;
import com.mwb.digitalstorage.database.DAO.DAO;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public abstract class BaseRepository
{
    private static DAO dao;
    final Executor executor;

    BaseRepository()
    {
        executor = Executors.newSingleThreadExecutor();
    }

    public static void setDao(Application application)
    {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.dao();
    }

    static DAO getDao() { return dao; }
}