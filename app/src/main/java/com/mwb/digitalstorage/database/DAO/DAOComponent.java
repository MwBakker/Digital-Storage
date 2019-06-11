package com.mwb.digitalstorage.database.DAO;

import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.model.ComponentCategory;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;


public interface DAOComponent
{
    //  insert component
    @Insert
    void insertComponent(Component component);

    //  get all components belonging to a certain rack
    @Query("SELECT id, rack_id, component_category_id, name, code, img_path " +
            "FROM component c " +
            "WHERE rack_id = :rackID " +
            "GROUP BY code ")
    LiveData<List<Component>> getComponents(long rackID);

    //  searches components based on string input
    @Query("SELECT id, rack_id, component_category_id, name, code, img_path " +
            "FROM component c " +
            "WHERE name LIKE :input or code LIKE :input " +
            "GROUP BY code ")
    List<Component> getComponents(String input);

    //  get the amount of components in Rack
    @Query("SELECT COUNT(id) FROM component WHERE rack_id = :rackID")
    Integer getAmountOfComponents(long rackID);

    //  get the amount of total components
    @Query("SELECT COUNT(id) FROM component")
    Integer getAmountOfComponents();

    //  get the amount of components
    @Query("SELECT COUNT(c.id) * COUNT(r.id) as totalComponents " +
            "FROM component c, rack r " +
            "WHERE r.id = :storageID")
    Integer getAmountOfComponentsInStorage(long storageID);




    //  get all components belonging to a rack and certain specific category
    @Query("SELECT id, rack_id, component_category_id, name, code, img_path, COUNT(id) as amount " +
            "FROM component c " +
            "WHERE c.rack_id = :rackID AND c.component_category_id = :categoryID " +
            "GROUP BY code ")
    LiveData<List<Component>> getFilteredComponents(long rackID, long categoryID);

    //  edit component
    @Query("UPDATE component SET component_category_id = :categoryID, name = :componentName, code = :componentCode, img_path = :componentImgPath " +
            "WHERE id = :componentID")
    void editComponent(long componentID, long categoryID, String componentName, String componentCode, String componentImgPath);

    //  remove component
    @Query("DELETE FROM component WHERE id = :componentID")
    void deleteComponent(long componentID);






    //  insert component category
    @Insert
    long insertComponentCategory(ComponentCategory componentCategory);

    //  get a certain category by name
    @Query("SELECT * FROM component_category WHERE name == :name")
    ComponentCategory getComponentCategory(String name);

    //  get a certain category by id
    @Query("SELECT * FROM component_category WHERE id == :id")
    ComponentCategory getComponentCategory(long id);

    //  get all the component categories
    @Query("SELECT * FROM component_category ORDER BY name ASC")
    LiveData<List<ComponentCategory>> getComponentCategories();

    //  get component categories that are being represented in the rack
    @Query("SELECT cc.id, cc.name, " +
            "       (SELECT COUNT(c.id) " +
            "        FROM component c " +
            "        WHERE c.rack_id = :rackID AND cc.id = c.component_category_id ) as component_amount " +
            "FROM component_category cc " +
            "INNER JOIN component c ON cc.id == c.component_category_id " +
            "WHERE c.rack_id = :rackID " +
            "GROUP BY cc.name ")
    LiveData<List<ComponentCategory>> getComponentCategories(long rackID);

    //  edit category component
    @Query("UPDATE component_category SET name = :catName " +
            "WHERE id = :catID")
    void editComponentCategory(long catID, String catName);
}
