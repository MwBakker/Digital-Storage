package com.mwb.digitalstorage.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.mwb.digitalstorage.database.DAO.DAO;
import com.mwb.digitalstorage.model.Company;
import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.model.ComponentCategory;
import com.mwb.digitalstorage.model.Rack;
import com.mwb.digitalstorage.model.Storage;
import com.mwb.digitalstorage.model.Tool;


@Database(entities = {Company.class, Storage.class, Rack.class, Component.class, ComponentCategory.class, Tool.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase
{
    public abstract DAO dao();

    // Just one instance
    static volatile RoomDB INSTANCE;

    // Callback (??)
    static RoomDatabase.Callback sStorageDBCallback =
            new RoomDatabase.Callback()
            {
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                new PopulateDbAsync(INSTANCE).execute();
                }
            };

    // getter of this object, just one instance
    public static RoomDB getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (RoomDB.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class, "storage_database")
                               .addCallback(sStorageDBCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    // inner-class, extends possibility of async (since only one can be extended at the time)
    // private so can't be a loose-filed class..
    // populates the DB
    static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {
        final DAO _dao;

        PopulateDbAsync(RoomDB db) {
            _dao = db.dao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}