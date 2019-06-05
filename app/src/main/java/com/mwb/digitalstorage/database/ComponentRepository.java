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


public class ComponentRepository extends BaseRepository
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
        return Transformations.map(getDao().getComponents(rackID), newData -> createComponentUI(newData));
    }

    //  returns components filtered by category
    public LiveData<List<UIComponent>> getCategoryFilteredComponents(long rackId, long catID)
    {
        // transform one list to another
        return Transformations.map(getDao().getFilteredComponents(rackId, catID), newData -> createComponentUI(newData));
    }

    //  manually sets the rack to rackUI
    private List<UIComponent> createComponentUI(List<Component> components)
    {
        List<UIComponent> UIComponentList = new ArrayList<>();

        executor.execute( () ->
        {
            for (Component component : components)
            {
                UIComponent uiComponent = new UIComponent(component.componentID, component.getName(), component.getCode(), component.getImgPath());
                uiComponent.setCategoryName(getDao().getComponentCategory(component.componentCategoryID).getName());
                UIComponentList.add(uiComponent);
            }
        });
        return UIComponentList;
    }

    //  component insert
    public void insertComponent(long rackID, long componentCatID, String componentCatName,
                                String componentCode, String componentImgPath, int count)
    {
        getDao().insertComponent(new Component(rackID, componentCatID, componentCatName, componentCode, componentImgPath, count));
    }

    //  component edit
    public void editComponent(long componentID, long componentCatID, String componentName, String componentCode, String componentImgPath)
    {
        getDao().editComponent(componentID, componentCatID, componentName, componentCode, componentImgPath);
    }

    //  delete component
    public void deleteComponent(long componentID) { getDao().deleteComponent(componentID); }


    //__________________________________________________________________________
    //                       //
    //  ComponentCategories  //
    //                       //
    //__________________________________________________________________________

    //  returns one component category per name
    public long getComponentCategory(String categoryName)
    {
        ComponentCategory componentCategory = getDao().getComponentCategory(categoryName);
        return (componentCategory != null) ? componentCategory.id : 0L;
    }

    //  returns all component categories belonging to rack
    public LiveData<List<UIComponentCategory>> getAllComponentCategories()
    {
        return Transformations.map(getDao().getComponentCategories(), newData -> createComponentCategoryUI(newData));
    }

    //  returns all categories of the components
    public LiveData<List<UIComponentCategory>> getRackComponentCategories(long rackID)
    {
        return Transformations.map(getDao().getComponentCategories(rackID), newData -> createComponentCategoryUI(newData));
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
       return getDao().insertComponentCategory(new ComponentCategory(componentCatName, 0));
    }

    //  edits the name of a component category per database
    public void editComponentCategory(long catID, String componentCatName)
    {
        getDao().editComponentCategory(catID, componentCatName);
    }
}