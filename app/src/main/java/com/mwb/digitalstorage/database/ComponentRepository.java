package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.model.ComponentCategory;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class ComponentRepository
{
    //__________________________________________________________________________
    //                      //
    //      Components      //
    //                      //
    //__________________________________________________________________________

    //  returns all components from rack
    public LiveData<List<UIComponent>> getRackComponents(long rackID)
    {
        return Transformations.map(BaseRepository.getDao().getRackComponents(rackID), newData -> createComponentUI(newData));
    }

    //  returns components filtered by category
    public LiveData<List<UIComponent>> getCatFilteredComponents(long rackId, long catID)
    {
        // transform one list to another
        return Transformations.map(BaseRepository.getDao().getFilteredComponents(rackId, catID), newData -> createComponentUI(newData));
    }

    //  get certain component
    public UIComponent getComponent(long componentID)
    {
        Component component = BaseRepository.getDao().getComponent(componentID);
        return new UIComponent(componentID, component.rackID, component.componentCategoryID, "", component.getCategoryName(),
                                component.getName(), component.getCode(), component.getImgPath(), component.getCount());
    }

    //  manually sets the rack to rackUI
    private List<UIComponent> createComponentUI(List<Component> components)
    {
        List<UIComponent> UIComponentList = new ArrayList<>();
        for (Component component : components)
        {
            UIComponentList.add(new UIComponent(component.componentID, component.rackID, component.componentCategoryID, "", component.getCategoryName(),
                                                component.getName(), component.getCode(), component.getImgPath(), component.getCount()));
        }
        return UIComponentList;
    }

    //  component insert
    public void insertComponent(long rackID, long componentCatID, String componentCatName, String componentName,
                                String componentCode, String componentImgPath, int count)
    {
        BaseRepository.getDao().insertComponent(new Component(rackID, componentCatID, componentCatName, componentName, componentCode, componentImgPath, count));
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

    //
    //  returns all component categories belonging to rack
    //
    public LiveData<List<UIComponentCategory>> getAllComponentCategories()
    {
        return Transformations.map(BaseRepository.getDao().getAllComponentCategories(), newData -> createComponentCatUI(newData));
    }

    //  returns all categories of the components
    public LiveData<List<UIComponentCategory>> getRackComponentCategories(long rackID)
    {
        return Transformations.map(BaseRepository.getDao().getRackComponentCategories(rackID), newData -> createComponentCatUI(newData));
    }

    //  gets one component category per name
    public long getComponentCategory(String categoryName)
    {
        ComponentCategory componentCategory = BaseRepository.getDao().getComponentCategory(categoryName);
        return (componentCategory != null) ? BaseRepository.getDao().getComponentCategory(categoryName).id : 0L;
    }

    //  transforms the Component list to a ComponentUI list
    private List<UIComponentCategory> createComponentCatUI(List<ComponentCategory> componentsCategories)
    {
        List<UIComponentCategory> UIComponentList = new ArrayList<>();

        for (ComponentCategory componentCategory : componentsCategories)
        {
            UIComponentList.add(new UIComponentCategory(componentCategory.id, componentCategory.getName(),
                                                    componentCategory.getAmountOfComponents()));
        }
        return UIComponentList;
    }

    //  performs category insert
    //  the generated ID after insert must be passed per callback
    public long insertComponentCategory(String componentCatName)
    {
       return BaseRepository.getDao().insertComponentCat(new ComponentCategory(componentCatName, 0));
    }

    //  edits the name of a component category per database
    public void editComponentCategory(long catID, String componentCatName)
    {
        BaseRepository.getDao().editComponentCategory(catID, componentCatName);
    }
}