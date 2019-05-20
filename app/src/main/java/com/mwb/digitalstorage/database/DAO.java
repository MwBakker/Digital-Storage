package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Company;
import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.model.ComponentCategory;
import com.mwb.digitalstorage.model.Entity;
import com.mwb.digitalstorage.model.Rack;
import com.mwb.digitalstorage.model.Storage;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface DAO
{
    //
    //  get company
    //
    @Query("SELECT * FROM company LIMIT 1")
    Company getUiCompany();

    //
    //  get all the storage units
    //
    @Query("SELECT * FROM storage ORDER BY name ASC")
    LiveData<List<Storage>> getAllStorageUnits();

    //
    //  get a storage unit
    //
    @Query("SELECT * FROM storage WHERE id == :storageID")
    Storage getStorageUnit(long storageID);

    //
    //  get all the racks from a certain storage
    //
    @Query("SELECT *, " +
            "       (SELECT COUNT(component.id) " +
            "        FROM component " +
            "        WHERE component.rack_id = rack_id) as component_count " +
            "FROM rack " +
            "WHERE rack.storage_id == :storageID ")
    LiveData<List<Rack>> getStorageRacks(long storageID);

    //
    //  get a rack
    //
    @Query("SELECT * FROM rack WHERE id == :rackID")
    Rack getRack(long rackID);

    //
    //  get all the component categories
    //
    @Query("SELECT * FROM component_category ORDER BY name ASC")
    LiveData<List<ComponentCategory>> getAllComponentCategories();

    //
    //  get component categories that are being represented in the rack
    //
    @Query("SELECT id, name, " +
            "       (SELECT COUNT(component.id) " +
            "        FROM component " +
            "        WHERE component.rack_id = :rackID AND component_category.id = component_category_id ) as component_amount " +
            "FROM component_category")
    LiveData<List<ComponentCategory>> getRackComponentCategories(long rackID);

    //
    //  get a certain category
    //
    @Query("SELECT * FROM component_category WHERE name == :componentCat")
    ComponentCategory getComponentCategory(String componentCat);

    //
    //  get all components belonging to a rack
    //
    @Query("SELECT c.id, COUNT(c.name) as count, cc.name as category_name, rack_id, component_category_id, c.name, c.img_path, code " +
            "FROM component c " +
            "INNER JOIN component_category cc ON cc.id = c.component_category_id " +
            "WHERE c.rack_id = :rackID and cc.id = c.component_category_id " +
            "GROUP BY c.name " +
            "ORDER BY c.name ASC")
    LiveData<List<Component>> getRackComponents(long rackID);

    //
    //  get all components belonging to a rack and certain category
    //
    @Query("SELECT c.id, COUNT(c.name) as count, cc.name as category_name, rack_id, component_category_id, c.name, c.img_path, code " +
            "FROM component c " +
            "INNER JOIN component_category cc ON cc.id = c.component_category_id " +
            "WHERE c.rack_id = :rackID and cc.id = :categoryID " +
            "GROUP BY c.name " +
            "ORDER BY c.name ASC")
    LiveData<List<Component>> getFilteredComponents(long rackID, long categoryID);

    //
    //  get all the entities according to the string input LIKE
    //   == does not work this way, unfortunatly ==
    //
    /*
    @Query("SELECT s.id, s.name, r.id, r.name, c.id, c.name " +
            "FROM storage s " +
            "INNER JOIN rack r ON r.storage_id = s.id " +
            "INNER JOIN component c ON c.rack_id = r.id " +
            "WHERE s.name LIKE :input OR r.name LIKE :input OR c.name LIKE :input")
    LiveData<List<Entity>> searchEntities(String input);
    */

    @Query("SELECT * FROM storage WHERE name LIKE :input ")
    List<Storage> searchStorages(String input);
    @Query("SELECT * FROM rack WHERE name LIKE :input ")
    List<Rack> searchRacks(String input);
    @Query("SELECT * FROM component WHERE name LIKE :input or code LIKE :input")
    List<Component> searchComponents(String input);

    //
    //  get component
    //
    @Query("SELECT * FROM component WHERE id == :componentID")
    Component getComponent(long componentID);

    //
    //  insert company
    //
    @Insert
    void insertCompany(Company company);

    //
    //  insert storage
    //
    @Insert
    long insertStorage(Storage storage);

    //
    //  insert rack
    //
    @Insert
    void insertRack(Rack rack);

    //
    //  insert component category
    //
    @Insert
    long insertComponentCat(ComponentCategory componentCategory);

    //
    //  insert component
    //
    @Insert
    void insertComponent(Component component);

    //
    //  edit company
    //
    @Query("UPDATE company SET name = :companyName, location = :companyLocation, img_path = :imgPath ")
    void editCompany(String companyName, String companyLocation, String imgPath);

    //
    //  edit storage
    //
    @Query("UPDATE storage SET name = :storageName, location = :storageLoc " +
           "WHERE id = :storageID")
    void editStorage(long storageID, String storageName, String storageLoc);

    //
    //  edit rack
    //
    @Query("UPDATE rack SET name = :rackName, img_path = :rackImgPath " +
            "WHERE id = :rackID")
    void editRack(long rackID, String rackName, String rackImgPath);

    //
    //  edit category component
    //
    @Query("UPDATE component_category SET name = :catName " +
            "WHERE id = :catID")
    void editComponentCategory(long catID, String catName);

    //
    //  edit component
    //
    @Query("UPDATE component SET component_category_id = :categoryID, name = :componentName, code = :componentCode, img_path = :componentImgPath " +
            "WHERE id = :componentID")
    void editComponent(long componentID, long categoryID, String componentName, String componentCode, String componentImgPath);

    //
    //  remove storage
    //
    @Query("DELETE FROM storage WHERE id = :storageID")
    void deleteStorage(long storageID);

    //
    //  remove rack
    //
    @Query("DELETE FROM rack WHERE id = :rackID")
    void deleteRack(long rackID);

    //
    //  remove component
    //
    @Query("DELETE FROM component WHERE id = :componentID")
    void deleteComponent(long componentID);
}