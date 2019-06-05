package com.mwb.digitalstorage.database.DAO;

import com.mwb.digitalstorage.model.Storage;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;


public interface DAOStorage
{
    //  insert storage
    @Insert
    long insertStorage(Storage storage);

    //  get a storage unit
    @Query("SELECT * FROM storage WHERE id == :storageID")
    Storage getStorageUnit(long storageID);

    //  get a storage unit
    @Query("SELECT * FROM storage WHERE name == :input")
    List<Storage>  getStorageUnits(String input);

    //  get all the storage units
    @Query("SELECT * FROM storage ORDER BY name ASC")
    LiveData<List<Storage>> getStorageUnits();

    //  get all the storage units count
    @Query("SELECT COUNT(id) FROM storage")
    Integer getAmountOfStorageUnits();


    //
    //  get all the entities according to the string input LIKE
    //   == does not work this way, unfortunately ==
    //
    /*
    @Query("SELECT s.id, s.name, r.id, r.name, c.id, c.name " +
            "FROM storage s " +
            "INNER JOIN rack r ON r.storage_id = s.id " +
            "INNER JOIN component c ON c.rack_id = r.id " +
            "WHERE s.name LIKE :input OR r.name LIKE :input OR c.name LIKE :input")
    LiveData<List<Entity>> searchEntities(String input);
    */


    //  edit storage
    @Query("UPDATE storage SET name = :storageName, location = :storageLoc " +
            "WHERE id = :storageID")
    void editStorage(long storageID, String storageName, String storageLoc);

    //  remove storage
    @Query("DELETE FROM storage WHERE id = :storageID")
    void deleteStorage(long storageID);
}