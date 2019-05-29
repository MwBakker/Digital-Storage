package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.model.ComponentCategory;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class ComponentRepository
{
    private Executor executor;


    public ComponentRepository(Executor executor)
    {
        this.executor = executor;
    }

    //__________________________________________________________________________
    //                      //
    //      Components      //
    //                      //
    //__________________________________________________________________________

    //  returns all components from specific rack
    public LiveData<List<UIComponent>> getRackComponents(long rackID)
    {
        return Transformations.map(BaseRepository.getDao().getRackComponents(rackID), newData -> createComponentUI(newData));
    }

    //  returns components filtered by category
    public LiveData<List<UIComponent>> getCategoryFilteredComponents(long rackId, long catID)
    {
        // transform one list to another
        return Transformations.map(BaseRepository.getDao().getFilteredComponents(rackId, catID), newData -> createComponentUI(newData));
    }

    //  get certain component
    public UIComponent getComponent(long componentID)
    {
        Component component = BaseRepository.getDao().getComponent(componentID);
        return new UIComponent(componentID, component.rackID, component.componentCategoryID, "", component.getName(),
                                component.getCode(), component.getImgPath(), component.getCount());
    }

    //  manually sets the rack to rackUI
    private List<UIComponent> createComponentUI(List<Component> components)
    {
        List<UIComponent> UIComponentList = new ArrayList<>();

        executor.execute( () ->
        {
            for (Component component : components)
            {
                String categoryName = BaseRepository.getDao().getComponentCategory(component.componentCategoryID).getName();
                UIComponentList.add(new UIComponent(component.componentID, component.rackID, component.componentCategoryID, categoryName,
                        component.getName(), component.getCode(), component.getImgPath(), component.getCount()));
            }
        });
        return UIComponentList;
    }

    //  component insert
    public void insertComponent(long rackID, long componentCatID, String componentCatName,
                                String componentCode, String componentImgPath, int count)
    {
        BaseRepository.getDao().insertComponent(new Component(rackID, componentCatID, componentCatName, componentCode, componentImgPath, count));
    }

    //  component edit
    public void editComponent(long componentID, long componentCatID, String componentName, String componentCode, String componentImgPath)
    {
        BaseRepository.getDao().editComponent(componentID, componentCatID, componentName, componentCode, componentImgPath);
    }

    //  delete component
    public void deleteComponent(long componentID) { BaseRepository.getDao().deleteComponent(componentID); }


    //__________________________________________________________________________
    //                       //
    //  ComponentCategories  //
    //                       //
    //__________________________________________________________________________

    //  returns one component category based on the id
    public String getComponentCategoryName(long id)
    {
        ComponentCategory componentCategory = BaseRepository.getDao().getComponentCategory(id);
        return (componentCategory != null) ? componentCategory.getName() : null;
    }

    //  returns one component category per name
    public long getComponentCategory(String categoryName)
    {
        ComponentCategory componentCategory = BaseRepository.getDao().getComponentCategory(categoryName);
        return (componentCategory != null) ? componentCategory.id : 0L;
    }

    //  returns all component categories belonging to rack
    public LiveData<List<UIComponentCategory>> getAllComponentCategories()
    {
        return Transformations.map(BaseRepository.getDao().getAllComponentCategories(), newData -> createComponentCategoryUI(newData));
    }

    //  returns all categories of the components
    public LiveData<List<UIComponentCategory>> getRackComponentCategories(long rackID)
    {
        return Transformations.map(BaseRepository.getDao().getRackComponentCategories(rackID), newData -> createComponentCategoryUI(newData));
    }

    //  transforms the Component list to a ComponentUI list
    private List<UIComponentCategory> createComponentCategoryUI(List<ComponentCategory> componentCategories)
    {
        List<UIComponentCategory> UIComponentCategoryList = new ArrayList<>();

        for (ComponentCategory componentCategory : componentCategories)
        {
            UIComponentCategoryList.add(new UIComponentCategory(componentCategory.id, componentCategory.getName(),
                                                    componentCategory.getAmountOfComponents()));
        }
        return UIComponentCategoryList;
    }

    //  performs category insert
    //  the generated ID after insert must be passed per callback
    public long insertComponentCategory(String componentCatName)
    {
       return BaseRepository.getDao().insertComponentCategory(new ComponentCategory(componentCatName, 0));
    }

    //  edits the name of a component category per database
    public void editComponentCategory(long catID, String componentCatName)
    {
        BaseRepository.getDao().editComponentCategory(catID, componentCatName);
    }
}