package com.mwb.digitalstorage.database.DAO;

import com.mwb.digitalstorage.model.Rack;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;


public interface DAORack
{
    //  insert rack
    @Insert
    void insertRack(Rack rack);

    //  get a rack
    @Query("SELECT * FROM rack WHERE id == :rackID")
    Rack getRack(long rackID);

    //  get all the racks from a certain storage
    @Query("SELECT * FROM rack r " +
            "WHERE r.storage_id == :storageID ")
    LiveData<List<Rack>> getRacks(long storageID);

    //  searches racks based on string input
    @Query("SELECT * FROM rack WHERE name LIKE :input ")
    List<Rack> getRacks(String input);

    //  gets the amount of racks in a storage unit
    @Query("SELECT COUNT(id) FROM rack WHERE id = :storageID ")
    Integer getAmountOfRacks(long storageID);

    //  gets the amount total of racks
    @Query("SELECT COUNT(id) FROM rack")
    Integer getAmountOfRacks();

    //  edit rack
    @Query("UPDATE rack SET name = :rackName, img_path = :rackImgPath " +
            "WHERE id = :rackID")
    void editRack(long rackID, String rackName, String rackImgPath);

    //  remove rack
    @Query("DELETE FROM rack WHERE id = :rackID")
    void deleteRack(long rackID);
}
